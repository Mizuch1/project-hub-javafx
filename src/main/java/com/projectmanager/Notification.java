package com.projectmanager;

import java.time.LocalDateTime;

public class Notification {
    private int id;
    private int userId;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;

    public Notification(int id, int userId, String message, LocalDateTime timestamp, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public Notification(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isRead() {
        return isRead;
    }
}
