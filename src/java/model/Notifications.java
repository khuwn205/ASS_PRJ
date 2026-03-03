package model;

import java.util.Date;

public class Notifications {

    private int notificationId;
    private int userId;
    private String title;
    private String message;
    private boolean isRead;
    private int relatedItemId;
    private Date createdAt;

    public Notifications() {
    }

    public Notifications(int notificationId, int userId, String title, String message, boolean isRead, Integer relatedItemId, Date createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
        this.relatedItemId = relatedItemId;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getRelatedItemId() {
        return relatedItemId;
    }

    public void setRelatedItemId(Integer relatedItemId) {
        this.relatedItemId = relatedItemId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notifications{" + "notificationId=" + notificationId + ", userId=" + userId + ", title=" + title + ", message=" + message + ", isRead=" + isRead + ", relatedItemId=" + relatedItemId + ", createdAt=" + createdAt + '}';
    }
}