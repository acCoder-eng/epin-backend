package com.epinmarketplace.notification_service.dto;

import java.io.Serializable;
import java.util.Map;

public class NotificationMessage implements Serializable {
    
    private Long userId;
    private String title;
    private String message;
    private String type;
    private Map<String, Object> data;
    
    // Constructors
    public NotificationMessage() {}
    
    public NotificationMessage(Long userId, String title, String message, String type) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
    }
    
    public NotificationMessage(Long userId, String title, String message, String type, Map<String, Object> data) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.data = data;
    }
    
    // Getters and Setters
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "NotificationMessage{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
