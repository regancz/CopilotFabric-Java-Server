package com.charles.copilotfabric.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author charles
 * @date 2024/1/23 1:06
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.charles.copilotfabric.dao"})
public class MongoConfiguration {
}
