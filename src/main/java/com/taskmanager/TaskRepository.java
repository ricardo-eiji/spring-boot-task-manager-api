

package com.taskmanager;

// This is related to Spring Data JPA

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> 
{
    
}
