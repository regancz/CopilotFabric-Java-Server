package com.charles.copilotfabric.api.service.impl;

import com.charles.copilotfabric.api.service.LogEntryService;
import com.charles.copilotfabric.dao.entity.LogEntry;
import com.charles.copilotfabric.dao.repository.LogEntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author charles
 * @date 2024/1/23 0:18
 */
@Service
public class LogEntryServiceImpl implements LogEntryService {
    @Autowired
    private LogEntryDao logEntryDao;

    public void processLogs(Date from, Date to) {
        List<LogEntry> logs = logEntryDao.findByTimestampBetween(from, to);
    }
}
