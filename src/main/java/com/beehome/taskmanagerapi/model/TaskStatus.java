package com.beehome.taskmanagerapi.model;

public enum TaskStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String description;

    // Construtor
    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}