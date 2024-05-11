package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.ActivityLog;

public interface ActivityLogService {
    void logActivity(ActivityLog activityLog);
    // Add other methods for retrieving activity logs
    
List<ActivityLog> getActivityLogsForUser(String userId, LocalDateTime startDate, LocalDateTime endDate);

}

