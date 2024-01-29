package com.charles.copilotfabric.dao.vo;

import com.charles.copilotfabric.dao.entity.LogEntry;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author charles
 * @date 2024/1/23 12:47
 */
@Document(collection = "logVo")
public class LogVo extends LogEntry {
    private String taskType;

    public LogVo(String id, String message, Date timestamp, String level, String taskType) {
        super(id, message, timestamp, level);
        this.taskType = taskType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
