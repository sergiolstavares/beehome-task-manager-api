package com.beehome.taskmanagerapi.controller;

import com.beehome.taskmanagerapi.dto.TaskRequest;
import com.beehome.taskmanagerapi.model.TaskModel;
import com.beehome.taskmanagerapi.model.TaskStatus;
import com.beehome.taskmanagerapi.security.JwtService;
import com.beehome.taskmanagerapi.service.TaskService;
import io.jsonwebtoken.Jwt;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.beehome.taskmanagerapi.util.ErrorUtil.createErrorResponse;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> listTasks(
            @Valid
            @PageableDefault(size = 10,
            sort = "deadline",
            direction = org.springframework.data.domain.Sort.Direction.ASC) Pageable pageable, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            UUID userId = jwtService.getUserIDFromToken(token);
            Page response = taskService.listTasks(pageable, userId);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> listTasksByStatus(@RequestParam TaskStatus status, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            UUID userId = jwtService.getUserIDFromToken(token);
            List<TaskModel> response = taskService.listTasksByStatus(status, userId);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @GetMapping("/{id:[0-9a-fA-F\\\\-]{36}}")
    public ResponseEntity<?> getTaskByID(@PathVariable UUID id) {
        try {
            Optional<TaskModel> task = taskService.getTaskByID(id);
            return ResponseEntity.ok(task);
        } catch (Exception ex) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest task, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            UUID userId = jwtService.getUserIDFromToken(token);
            TaskModel createdTask = taskService.createTask(task, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception ex) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @PutMapping("/{id:[0-9a-fA-F\\\\-]{36}}")
    public ResponseEntity<?> updateTask(@PathVariable UUID id, @RequestBody TaskModel task, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            UUID userId = jwtService.getUserIDFromToken(token);
            TaskModel updatedTask = taskService.updateTask(id, task, userId);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception ex) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @DeleteMapping("/{id:[0-9a-fA-F\\\\-]{36}}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

}
