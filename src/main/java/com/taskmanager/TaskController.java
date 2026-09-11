
package com.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/tasks")

public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<TaskResponseDTO> getAllTasks()
    {
        return taskRepository.findAll()
            .stream()
            .map(TaskResponseDTO::new)
            .collect(Collectors.toList());
    }

    @PostMapping
    public TaskResponseDTO addTask (@Valid @RequestBody TaskRequestDTO request)
    {
        // return taskRepository.save(task);
        Task task = new Task(request.getTitle());
        Task saved = taskRepository.save(task);
        return new TaskResponseDTO(saved);
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTask(@PathVariable int id)
    {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        return new TaskResponseDTO(task);
        // return taskRepository.findById(id)
        //     .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @PutMapping("/{id}/complete")
    public TaskResponseDTO completeTask(@PathVariable int id)
    {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(TaskStatus.DONE);
        Task saved = taskRepository.save(task);
        return new TaskResponseDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id)
    {
        taskRepository.deleteById(id);
    }
}
