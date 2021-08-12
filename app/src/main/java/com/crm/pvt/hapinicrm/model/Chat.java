package com.crm.pvt.hapinicrm.model;

public class Chat {
    private String sendersName;
    private String Message;

    public Chat() { }

    public Chat(String sendersName, String message) {
        this.sendersName = sendersName;
        Message = message;
    }

    public String getSendersName() {
        return sendersName;
    }

    public void setSendersName(String sendersName) {
        this.sendersName = sendersName;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
