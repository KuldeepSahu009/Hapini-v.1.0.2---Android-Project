package com.crm.pvt.hapinicrm.model;

public class Chat {
    private String senderPasscode;
    private String sendersName;
    private String Message;

    public Chat() { }

    public Chat(String senderPasscode,String sendersName, String message) {
        this.senderPasscode = senderPasscode;
        this.sendersName = sendersName;
        Message = message;
    }

    public String getSenderPasscode() {
        return senderPasscode;
    }

    public void setSenderPasscode(String senderPasscode) {
        this.senderPasscode = senderPasscode;
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
