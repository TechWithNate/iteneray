package com.tech.nate.iteneraysystem;

import java.time.LocalDateTime;

public class Model {

        private String taskName;
        private String taskDescription;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Priority priority;
        private TaskState taskState;

        // Constructors, getters, and setters


    public Model() {
    }

    // Constructor without endTime (for tasks without a specific end time)
        public Model(String taskName, String taskDescription, LocalDateTime startTime, Priority priority, TaskState taskState) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.startTime = startTime;
            this.priority = priority;
            this.taskState = taskState;
        }

        // Constructor with endTime (for tasks with a specific end time)
        public Model(String taskName, String taskDescription, LocalDateTime startTime, LocalDateTime endTime, Priority priority, TaskState taskState) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.startTime = startTime;
            this.endTime = endTime;
            this.priority = priority;
            this.taskState = taskState;
        }

        // Other methods if needed

        // Enum for task priority
        public enum Priority {
            HIGH, MEDIUM, LOW
        }

        // Enum for task state
        public enum TaskState {
            COMPLETED, INCOMPLETE
        }

        // Getters and setters

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public Priority getPriority() {
            return priority;
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public TaskState getTaskState() {
            return taskState;
        }

        public void setTaskState(TaskState taskState) {
            this.taskState = taskState;
        }


}
