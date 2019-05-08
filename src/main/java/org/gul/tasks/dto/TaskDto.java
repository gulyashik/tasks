package org.gul.tasks.dto;

public class TaskDto {
    public String id;
    public String timestamp;
    public String status;

    public TaskDto(String id, String timestamp, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.status = status;
    }
}
