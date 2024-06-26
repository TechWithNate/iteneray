package com.tech.nate.iteneraysystem;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class Model implements Parcelable {

    private String taskId;
    private String taskName;
    private String taskDescription;
    private String startTime;
    private String endTime;
    private Priority priority;
    private TaskState taskState;
    private Boolean getAlert;
    private String date;
    private String day;
    private String displayDate;



    public Model() {
    }

    public Model(String taskId, String taskName, String taskDescription, String startTime, String endTime, Priority priority, TaskState taskState, Boolean getAlert, String date, String day) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.taskState = taskState;
        this.getAlert = getAlert;
        this.date = date;
        this.day = day;
    }

    protected Model(Parcel in) {
        taskId = in.readString();
        taskName = in.readString();
        taskDescription = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        byte tmpGetAlert = in.readByte();
        getAlert = tmpGetAlert == 0 ? null : tmpGetAlert == 1;
        date = in.readString();
        day = in.readString();
        displayDate = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(taskId);
        dest.writeString(taskName);
        dest.writeString(taskDescription);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeByte((byte) (getAlert == null ? 0 : getAlert ? 1 : 2));
        dest.writeString(date);
        dest.writeString(day);
        dest.writeString(displayDate);
    }

    // Enum for task priority
    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    // Enum for task state
    public enum TaskState {
        COMPLETED, INCOMPLETE
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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

    public Boolean getGetAlert() {
        return getAlert;
    }

    public void setGetAlert(Boolean getAlert) {
        this.getAlert = getAlert;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }
}
