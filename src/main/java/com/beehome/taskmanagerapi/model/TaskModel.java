package com.beehome.taskmanagerapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="task", schema = "register")
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Size(max = 100)
    @Column(name = "title")
    private String title;

    @NotNull
    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @NotNull
    @Column(name = "assigned_to")
    private UUID assignedTo;

    public TaskModel() {}

    public TaskModel(String title, String description, TaskStatus status, LocalDateTime createdOn, LocalDateTime deadline, UUID assignedTo) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdOn = createdOn;
        this.deadline = deadline;
        this.assignedTo = assignedTo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public UUID getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UUID assignedTo) {
        this.assignedTo = assignedTo;
    }
}
