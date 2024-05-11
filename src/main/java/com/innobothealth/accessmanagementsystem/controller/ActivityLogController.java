package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.ActivityLog;
import com.innobothealth.accessmanagementsystem.service.ActivityLogService;
import com.innobothealth.accessmanagementsystem.util.Event;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("activity-log")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @PostMapping("/log")
    public ResponseEntity<?> logActivity(@RequestParam Event event, @RequestParam String userId) {
        activityLogService.logActivity(ActivityLog.builder().userId(userId).event(event).timestamp(LocalDateTime.now()).build());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ActivityLog>> getActivityLogsForUser(
        @PathVariable String userId) {
        List<ActivityLog> activityLogs = activityLogService.getActivityLogsForUser(userId);
        return ResponseEntity.ok(activityLogs);
    }

}
