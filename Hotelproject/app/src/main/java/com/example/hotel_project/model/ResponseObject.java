package com.example.hotel_project.model;

public class ResponseObject<T> {

    private String status;
    private String message;
    private T data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
