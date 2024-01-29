package com.charles.copilotfabric.dao.repository;

import com.charles.copilotfabric.dao.vo.LogVo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * @author charles
 * @date 2024/1/23 14:07
 */
public interface LogVoDao extends MongoRepository<LogVo, String> {

    List<LogVo> findByTimestampBetween(Date from, Date to);
}
