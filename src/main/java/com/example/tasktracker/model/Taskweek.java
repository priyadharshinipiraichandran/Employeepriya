package com.example.tasktracker.model;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Taskweek {
    private int id;
    private int employeeId;
    private String project;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String category;
    private String description;

    // Constructors, getters, setters omitted for brevity

    // Method to calculate duration in seconds
    public long getDurationInSeconds() {
        Duration duration = Duration.between(startTime, endTime);
        return duration.getSeconds();
    }
}

