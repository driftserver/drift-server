package io.drift.elasticsearch;

import java.io.Serializable;

public class Hit implements Serializable{

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
