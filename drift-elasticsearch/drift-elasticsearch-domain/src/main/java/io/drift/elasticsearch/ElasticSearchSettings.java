package io.drift.elasticsearch;

import io.drift.core.recording.SubSystemDescription;
import io.drift.core.system.SubSystemConnectionDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ElasticSearchSettings implements SubSystemConnectionDetails, SubSystemDescription, Serializable {

    private String userName;

    private String password;

    private String url;

    private List<String> indices = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addIndex(String index) {
        indices.add(index);
    }

    public List<String> getIndices() {
        return indices;
    }

}
