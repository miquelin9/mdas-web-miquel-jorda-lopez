package com.ccm.user.user.domain.entities;

public class Message {

    public Message(Object content) {
        this.content = content;
    }

    private final Object content;

    public Object getContent() {
        return content;
    }
}
