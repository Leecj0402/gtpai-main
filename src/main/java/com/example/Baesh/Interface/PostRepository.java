package com.example.Baesh.Interface;

import com.example.Baesh.Entity.PostEntity;
import com.example.Baesh.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
    List<PostEntity> findAllByAuthorId(UserEntity user);
}
