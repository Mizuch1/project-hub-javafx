package com.projectmanager;

import java.time.LocalDate;

public class Project {
    private int id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private String budget;
    private int createdBy;
    private String createdByName;

    public Project(int id, String name, String description, LocalDate dueDate, String budget, int createdBy, String createdByName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.budget = budget;
        this.createdBy = createdBy;
        this.createdByName = createdByName;
    }

    public Project(String name, String description, LocalDate dueDate, String budget) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.budget = budget;
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

    public String getBudget() {
        return budget;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public String getCreatedByName() {
        return createdByName;
    }
}
