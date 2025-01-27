package com.beehome.taskmanagerapi.service;

import com.beehome.taskmanagerapi.dto.TaskRequest;
import com.beehome.taskmanagerapi.model.TaskModel;
import com.beehome.taskmanagerapi.model.TaskStatus;
import com.beehome.taskmanagerapi.repository.TaskRepository;
import com.beehome.taskmanagerapi.util.DateUtil;
import com.beehome.taskmanagerapi.util.TaskStatusUtil;
import com.beehome.taskmanagerapi.validate.TaskValidate;
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

    @Autowired
    private TaskValidate taskValidate;

    public Page<TaskModel> listTasks(Pageable pageable, UUID user) {
        taskValidate.listTasksValidate(pageable);

        return taskRepository.findByAssignedTo(user, pageable);
    }

    public List<TaskModel> listTasksByStatus(TaskStatus status, UUID user) {
        taskValidate.listTasksByStatusValidate(String.valueOf(status));

        return taskRepository.findTasksByStatus(status, user);
    }

    public Optional<TaskModel> getTaskByID(UUID id) {
        taskValidate.getTaskByIDValidate(id);

        return taskRepository.findById(id);
    }

    public TaskModel createTask(TaskRequest task) {
        taskValidate.createValidate(task);

        TaskModel taskModel = new TaskModel(
                task.getTitle(),
                task.getDescription(),
                TaskStatusUtil.decodeTaskStatus(task.getStatus()),
                DateUtil.generateDateTime(),
                task.getDeadline(),
                task.getAssignedTo());

        return taskRepository.save(taskModel);
    }

    public TaskModel updateTask(UUID id, TaskModel task) {
        taskValidate.validateUpdateTask(id, task);
        Optional<TaskModel> existingTask = taskRepository.findById(id);

        if (existingTask.isPresent()) {
            TaskModel taskUpdated = existingTask.get();

            Optional.ofNullable(task.getTitle()).ifPresent(taskUpdated::setTitle);
            Optional.ofNullable(task.getDescription()).ifPresent(taskUpdated::setDescription);
            Optional.ofNullable(task.getStatus()).ifPresent(taskUpdated::setStatus);
            Optional.ofNullable(task.getDeadline()).ifPresent(taskUpdated::setDeadline);
            Optional.ofNullable(task.getAssignedTo()).ifPresent(taskUpdated::setAssignedTo);

            return taskRepository.save(taskUpdated);
        } else {
            throw new EntityNotFoundException("Task with ID " + id + " not found");
        }
    }

    public void deleteTask(UUID id) {
        taskValidate.validateDeleteTask(id);
        taskRepository.deleteById(id);
    }
}
