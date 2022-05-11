package com.sm.response;

import com.google.gson.JsonElement;

public class GenericResponse {
    private StatusResponse status;
    private String message;
    private JsonElement data;

    public GenericResponse(StatusResponse status) {
        this.status = status;
    }

    public GenericResponse(StatusResponse status,String message) {
        this(status);
        this.message = message;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
