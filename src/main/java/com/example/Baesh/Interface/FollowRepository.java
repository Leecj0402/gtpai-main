package com.example.Baesh.Interface;

import com.example.Baesh.Entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity,Long> {
    List<FollowEntity> findByFollowingId(Long followingId);
    List<FollowEntity> findByFollowerId(Long followerId);
    FollowEntity findByFollowerIdAndFollowingId(Long user1, Long user2);
}
