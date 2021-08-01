package com.sh.journalmotherapp.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReminderModel implements Serializable {

    private String message;
    private int hour;
    private int minute;

    public ReminderModel() {
    }

    public ReminderModel(String message, int hour, int minute) {
        this.message = message;
        this.hour = hour;
        this.minute = minute;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
