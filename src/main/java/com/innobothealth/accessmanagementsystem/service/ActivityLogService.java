package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.ActivityLog;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityLogService {
    void logActivity(ActivityLog activityLog);
    // Add other methods for retrieving activity logs
    
    List<ActivityLog> getActivityLogsForUser(String userId);

}

