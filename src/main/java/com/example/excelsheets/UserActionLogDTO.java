package com.example.excelsheets;

import java.math.BigInteger;

public class UserActionLogDTO {
    private String id;
    private String email;
    private String type;
    private String action;
    private BigInteger created_timestamp;
    private String message;
    private String status;

    public UserActionLogDTO(String id, String email, String type, String action, BigInteger created_timestamp, String message, String status) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.action = action;
        this.created_timestamp = created_timestamp;
        this.message = message;
        this.status = status;
    }

    public UserActionLogDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public BigInteger getCreated_timestamp() {
        return created_timestamp;
    }

    public void setCreated_timestamp(BigInteger created_timestamp) {
        this.created_timestamp = created_timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
