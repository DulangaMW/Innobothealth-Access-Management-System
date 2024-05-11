package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.ActivityLog;
import com.innobothealth.accessmanagementsystem.service.ActivityLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("activity-log")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @PostMapping("/log")
    public ResponseEntity<?> logActivity(@RequestBody ActivityLog activityLog) {
        activityLogService.logActivity(activityLog);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Implement endpoints for retrieving activity logs if needed

    @GetMapping("/{userId}")
    public ResponseEntity<List<ActivityLog>> getActivityLogsForUser(
        @PathVariable String userId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
    List<ActivityLog> activityLogs = activityLogService.getActivityLogsForUser(userId, startDate, endDate);
    return ResponseEntity.ok(activityLogs);
}
}
