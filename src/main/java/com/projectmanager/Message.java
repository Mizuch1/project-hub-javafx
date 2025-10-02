package com.projectmanager;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private int senderId;
    private int receiverId;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;
    private String senderName;
    private String receiverName;

    public Message(int id, int senderId, int receiverId, String content, LocalDateTime timestamp, boolean isRead, String senderName, String receiverName) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public Message(String sender, String receiver, String content) {
        this.senderName = sender;
        this.receiverName = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getSender() {
        return senderName;
    }

    public String getReceiver() {
        return receiverName;
    }
}
