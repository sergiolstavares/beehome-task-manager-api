package com.beehome.taskmanagerapi.controller;

import com.beehome.taskmanagerapi.dto.TaskRequest;
import com.beehome.taskmanagerapi.model.TaskModel;
import com.beehome.taskmanagerapi.model.TaskStatus;
import com.beehome.taskmanagerapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public Page<TaskModel> listTasks(
            @Valid
            @PageableDefault(size = 10,
            sort = "createdOn",
            direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        return taskService.listTasks(pageable);
    }

    @GetMapping("/filter")
    public List<TaskModel> listTasksByStatus(@Valid @RequestParam TaskStatus status) {
        return taskService.listTasksByStatus(status);
    }

    @GetMapping("/{id:[0-9a-fA-F\\\\-]{36}}")
    public Optional<TaskModel> getTaskByID(@Valid @PathVariable UUID id) {
        return taskService.getTaskByID(id);
    }

    @PostMapping
    public TaskModel createTask(@Valid @RequestBody TaskRequest task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id:[0-9a-fA-F\\\\-]{36}}")
    public TaskModel updateTask(@PathVariable UUID id, @RequestBody TaskModel task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id:[0-9a-fA-F\\\\-]{36}}")
    public void deleteTask(@Valid @PathVariable UUID id) {
        taskService.deleteTask(id);
    }
}
