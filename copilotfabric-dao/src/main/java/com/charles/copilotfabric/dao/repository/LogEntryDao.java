package com.charles.copilotfabric.dao.repository;

import com.charles.copilotfabric.dao.entity.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author charles
 * @date 2024/1/23 0:14
 */

public interface LogEntryDao extends MongoRepository<LogEntry, String> {

    List<LogEntry> findByTimestampBetween(Date from, Date to);
}
