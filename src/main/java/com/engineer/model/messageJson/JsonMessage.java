package com.engineer.model.messageJson;

/**
 * Created by Konrad on 14.11.2015.
 */

public class JsonMessage<T> {
    private String username;
    private String token;
    private T object;

    public JsonMessage() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
