package com.beehome.taskmanagerapi.validate;

import com.beehome.taskmanagerapi.dto.TaskRequest;
import com.beehome.taskmanagerapi.model.TaskModel;
import com.beehome.taskmanagerapi.model.TaskStatus;
import com.beehome.taskmanagerapi.repository.TaskRepository;
import com.beehome.taskmanagerapi.util.DateUtil;
import com.beehome.taskmanagerapi.util.TaskStatusUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskValidate {
    @Autowired
    private TaskRepository taskRepository;

    public void listTasksValidate(Pageable pageable) {
        if (pageable.getPageNumber() < 0) {
            throw new ValidationException("O número da pagina não pode ser negativo");
        }
        if (pageable.getPageSize() <= 0) {
            throw new ValidationException("O Tamanho da página deve ser maior que 0");
        }
        if (pageable.getPageSize() > 100) {
            throw new ValidationException("O tamanho da página excedeu o limite");
        }
    }

    public void listTasksByStatusValidate(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("O status não pode ser vazio");
        }

        try {
            TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status + ". Allowed values are: "
                    + Arrays.toString(TaskStatus.values()));
        }
    }

    public void getTaskByIDValidate(UUID id) {
        if (id == null || id.toString().isEmpty()) {
            throw new IllegalArgumentException("O indentificador deve ser informado");
        }
    }

    public void createValidate(TaskRequest taskRequest) {
        if (taskRequest.getTitle() == null || taskRequest.getTitle().isEmpty()) {
            throw new ValidationException("O titulo deve ser informado");
        }

        if (!isTitleUnique(taskRequest.getTitle())) {
            throw new ValidationException("O titulo já existe");
        }

        if (taskRequest.getStatus() == null) {
            throw new ValidationException("O status deve ser informado");
        }

        if (taskRequest.getDeadline().isBefore(DateUtil.generateDateTime())) {
            throw new IllegalArgumentException("A data de prazo não pode ser no passado.");
        }
    }

    public void validateUpdateTask(UUID id, TaskModel task) {
        if (id == null) {
            throw new IllegalArgumentException("O indentificador deve ser informado.");
        }

        Optional<TaskModel> existingTask = taskRepository.findById(id);
        if (existingTask.isEmpty()) {
            throw new EntityNotFoundException("Indentificador " + id + " não encontrado.");
        }

        Optional.ofNullable(task.getTitle()).ifPresent(title -> {
            if (title.trim().isEmpty()) {
                throw new IllegalArgumentException("O título deve ser informado.");
            }
        });

        Optional.ofNullable(task.getDescription()).ifPresent(description -> {
            if (description.trim().isEmpty()) {
                throw new IllegalArgumentException("A descrição deve ser informada.");
            }
        });

        Optional.ofNullable(task.getStatus()).ifPresent(status -> {
            try {
                TaskStatus.valueOf(status.getDescription().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status inválido: " + status + ". Valores permitidos são: "
                        + Arrays.toString(TaskStatus.values()));
            }
        });

        Optional.ofNullable(task.getDeadline()).ifPresent(deadline -> {
            if (deadline.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("A data de prazo não pode ser no passado.");
            }
        });
    }

    public void validateDeleteTask(UUID id) {
        // Valida se o ID é válido
        if (id == null) {
            throw new IllegalArgumentException("O ID da tarefa não pode ser nulo.");
        }
    }

    private boolean isTitleUnique(String title) {
        return taskRepository.findByTitle(title).isEmpty();
    }
}
