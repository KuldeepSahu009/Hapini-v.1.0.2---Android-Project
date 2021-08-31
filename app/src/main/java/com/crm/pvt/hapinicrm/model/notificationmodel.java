package com.crm.pvt.hapinicrm.model;

public class notificationmodel {
private String passcode;
    private String lastlogin;
    private String lastlogout;
    private String totalworktime;

    public notificationmodel() {
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getLastlogout() {
        return lastlogout;
    }

    public void setLastlogout(String lastlogout) {
        this.lastlogout = lastlogout;
    }

    public String getTotalworktime() {
        return totalworktime;
    }

    public void setTotalworktime(String totalworktime) {
        this.totalworktime = totalworktime;
    }
}
