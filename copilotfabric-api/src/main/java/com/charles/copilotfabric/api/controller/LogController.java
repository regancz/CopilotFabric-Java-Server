package com.charles.copilotfabric.api.controller;

import com.charles.copilotfabric.api.utils.Result;
import com.charles.copilotfabric.dao.vo.LogVo;
import com.charles.copilotfabric.dao.repository.LogEntryDao;
import com.charles.copilotfabric.dao.repository.LogVoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author charles
 * @date 2024/1/23 0:20
 */
@RequestMapping("/log")
@RestController
public class LogController {
    @Autowired
    private LogEntryDao logEntryDao;

    @Autowired
    private LogVoDao logVoDao;

    @RequestMapping(value = "/findByTimestampBetween", method = RequestMethod.GET)
    public void findByTimestampBetween(@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        logEntryDao.findByTimestampBetween(from, to);
    }

    @RequestMapping(value = "/queryAllLog", method = RequestMethod.GET)
    public Result<List<LogVo>> queryAllLog() {
        List<LogVo> logVoDaoAll = logVoDao.findAll();
        return Result.success(logVoDaoAll);
    }

    @RequestMapping(value = "/insertByLog", method = RequestMethod.GET)
    public void insertByLog() {
        insertLogsFromDirectory("F:\\Project\\PythonProject\\Auto-Tuning-HLF\\Model\\log\\sampling\\AdaBoost");
    }

    public void insertLogsFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] logFiles = directory.listFiles((dir, name) -> name.endsWith(".log"));

            if (logFiles != null) {
                for (File logFile : logFiles) {
                    insertLogsFromFile(logFile);
                }
            }
        } else {
            System.err.println("Invalid directory path.");
        }
    }

    private void insertLogsFromFile(File logFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
                String[] parts = line.split(" ", 2);
                LocalDateTime dateTime = LocalDateTime.parse(parts[0], formatter);
                Date date = java.sql.Timestamp.valueOf(dateTime);
                String id = UUID.randomUUID().toString();
                String level = "INFO";
                String taskType = "Optimizer";
                LogVo logVo = new LogVo(id, parts[1], date, level, taskType);
                logVoDao.insert(logVo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
