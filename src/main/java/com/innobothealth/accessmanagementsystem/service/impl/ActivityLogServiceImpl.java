package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.ActivityLog;
import com.innobothealth.accessmanagementsystem.repository.ActivityLogRepository;
import com.innobothealth.accessmanagementsystem.service.ActivityLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogServiceImpl(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @Override
    public void logActivity(ActivityLog activityLog) {
        activityLogRepository.save(activityLog);
    }

   @Override
    public List<ActivityLog> getActivityLogsForUser(String userId) {
        return activityLogRepository.findByUserId(userId);
    }

}
