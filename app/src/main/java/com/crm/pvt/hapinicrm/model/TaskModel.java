package com.crm.pvt.hapinicrm.model;

import java.io.Serializable;

public class TaskModel implements Serializable {

    private String taskAssignedTo;
    private String customerName;
    private String customerNumber;
    private String customerCity;
    private String task;
    private String taskProgress;
    private String taskFeedback;

    public TaskModel() {
    }

    public TaskModel(
            String taskAssignedTo,
            String customerName,
            String customerNumber,
            String customerCity,
            String task,
            String taskProgress,
            String taskFeedback
    ) {
        this.taskAssignedTo = taskAssignedTo;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        if (customerCity.isEmpty()) {
            this.customerCity = "City not mentioned";
        } else {
            this.customerCity = customerCity;
        }
        this.task = task;
        this.taskProgress = taskProgress;
        this.taskFeedback = taskFeedback;
    }

    public String getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(String taskProgress) {
        this.taskProgress = taskProgress;
    }

    public String getTaskFeedback() {
        return taskFeedback;
    }

    public void setTaskFeedback(String taskFeedback) {
        this.taskFeedback = taskFeedback;
    }

    public String getTaskAssignedTo() {
        return taskAssignedTo;
    }

    public void setTaskAssignedTo(String taskAssignedTo) {
        this.taskAssignedTo = taskAssignedTo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
