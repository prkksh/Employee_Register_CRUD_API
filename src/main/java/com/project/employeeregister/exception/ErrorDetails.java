package com.project.employeeregister.exception;

import java.util.Date;

//Custom Error Message
public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timeStamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
