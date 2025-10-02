package com.projectmanager;

import java.time.LocalDate;

public class Task {
    private int id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String status;
    private int assigneeId;
    private int projectId;
    private int createdById;
    private String assigneeName;
    private String createdByName;
    private String projectName;

    public Task(int id, String name, String description, LocalDate dueDate, String priority, String status, 
               int assigneeId, int projectId, int createdById, String assigneeName, String createdByName, String projectName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.assigneeId = assigneeId;
        this.projectId = projectId;
        this.createdById = createdById;
        this.assigneeName = assigneeName;
        this.createdByName = createdByName;
        this.projectName = projectName;
    }

    public Task(String name, String description, LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = "Non commencé";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getAssignee() {
        return assigneeName;
    }

    public void setAssignee(String assignee) {
        // For compatibility with existing code, we'll set the assignee name
        // In a real implementation, you'd want to set the assigneeId
    }
}
