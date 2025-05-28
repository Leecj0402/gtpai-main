package com.example.Baesh.Interface;

import com.example.Baesh.Entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Long> {
    // 사용자가 포함된 채팅방 조회
    @Query("SELECT c FROM ChatRoomEntity c WHERE c.user1.id = :userId OR c.user2.id = :userId")
    List<ChatRoomEntity> findByUserId (@Param("userId")Long userId);

    // 특정 두 사용자의 1대1 채팅방을 조회
    Optional<ChatRoomEntity> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
    Optional<ChatRoomEntity> findByUser1IdAndUser2IdOrUser1IdAndUser2Id(Long user1Id, Long user2Id, Long user2IdAlt, Long user1IdAlt);
}
