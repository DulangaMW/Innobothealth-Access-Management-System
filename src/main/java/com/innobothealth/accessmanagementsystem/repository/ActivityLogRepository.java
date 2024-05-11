package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.ActivityLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ActivityLogRepository extends MongoRepository<ActivityLog, String> {


    List<ActivityLog> findByUserIdAndTimestampBetween(String userId, LocalDateTime from, LocalDateTime to);
    List<ActivityLog> findByUserId(String userId);

}
