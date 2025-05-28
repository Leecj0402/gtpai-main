package com.example.Baesh.Interface;

import com.example.Baesh.Entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    List<MessageEntity> findByChatRoomIdOrderBySentTime(Long chatRoomId);
}
