package com.crm.pvt.hapinicrm.model;

public class TaskModel {
    String taskAssignedTo;
    String customerName;
    String customerNumber;
    String customerCity;
    String task;

    public TaskModel(String taskAssignedTo, String customerName, String customerNumber, String customerCity, String task) {
        this.taskAssignedTo = taskAssignedTo;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.customerCity = customerCity;
        this.task = task;
    }
}
