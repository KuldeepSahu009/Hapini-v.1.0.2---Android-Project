package com.crm.pvt.hapinicrm.model;

public class FeedbackModel {

    private String customerName;
    private String customerNumber;
    private String customerCity;
    private String task;
    private String feedback;

    public FeedbackModel() { }

    public FeedbackModel(
            String customerName,
            String customerNumber,
            String customerCity,
            String task,
            String feedback
    ) {
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        if(customerCity.isEmpty()) { this.customerCity = "City not mentioned"; }
        else { this.customerCity = customerCity; }
        this.task = task;
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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
