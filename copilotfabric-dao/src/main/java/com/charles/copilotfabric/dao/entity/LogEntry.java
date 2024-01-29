package com.charles.copilotfabric.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author charles
 * @date 2024/1/23 0:11
 */
@Document(collection = "logs")
public class LogEntry {
    public LogEntry(String id, String message, Date timestamp) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.level = "INFO";
    }

    public LogEntry(String id, String message, Date timestamp, String level) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.level = level;
    }

    @Id
    private String id;
    private String message;
    private Date timestamp;
//    level会被过滤掉
    @JsonProperty("level")
    private String level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}