package com.example.Baesh.Interface;

import com.example.Baesh.Entity.UserBasedAIRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBasedAIRecordRepository extends JpaRepository<UserBasedAIRecordEntity,Long> {
    List<UserBasedAIRecordEntity> findByUserIdOrderByCreatedTimeDesc(Long userId);
}
