package com.beehome.taskmanagerapi.service;

import com.beehome.taskmanagerapi.model.TaskModel;
import com.beehome.taskmanagerapi.model.TaskStatus;
import com.beehome.taskmanagerapi.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Page<TaskModel> listTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public List<TaskModel> listTasksByStatus(TaskStatus status) {
        return taskRepository.findTasksByStatus(status);
    }

    public Optional<TaskModel> getTaskByID(UUID id) {
        return taskRepository.findById(id);
    }

    public TaskModel createTask(TaskModel task) {
        return taskRepository.save(task);
    }

    public TaskModel updateTask(UUID id, TaskModel task) {
        Optional<TaskModel> existingTask = taskRepository.findById(id);

        if (existingTask.isPresent()) {
            TaskModel taskUpdated = existingTask.get();
            taskUpdated.setTitle(task.getTitle());
            taskUpdated.setDescription(task.getDescription());
            taskUpdated.setStatus(task.getStatus());
            taskUpdated.setDeadline(task.getDeadline());
            taskUpdated.setAssignedTo(task.getAssignedTo());

            return taskRepository.save(taskUpdated);
        } else {
            throw new EntityNotFoundException("Task with ID " + id + " not found");
        }
    }

    public void deleteTask(UUID Id) {
        taskRepository.deleteById(Id);
    }
}
