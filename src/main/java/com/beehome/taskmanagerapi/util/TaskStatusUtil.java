package com.beehome.taskmanagerapi.util;

import com.beehome.taskmanagerapi.model.TaskStatus;

public class TaskStatusUtil {
    public static TaskStatus decodeTaskStatus(String status) {
        return TaskStatus.valueOf(status.toUpperCase());
    }
}
