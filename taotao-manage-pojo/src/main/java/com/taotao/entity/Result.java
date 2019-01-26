package com.taotao.entity;

import java.io.Serializable;

public class Result implements Serializable {

    private boolean success;
    private String message;
    private String status;

    public Result(String status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
