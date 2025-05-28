package com.example.Baesh.Interface;

import com.example.Baesh.Entity.ChatHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistoryEntity,Long> {
    @Query("SELECT c FROM ChatHistoryEntity c WHERE c.userId.id = :userId")
    List<ChatHistoryEntity> findByUserId(@Param("userId")Long userId);
}
