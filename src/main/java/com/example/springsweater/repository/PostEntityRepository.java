package com.example.springsweater.repository;

import com.example.springsweater.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findByTag(String tag, Pageable pageable);
    Page<PostEntity> findByContent(String content, Pageable pageable);
    Page<PostEntity> findByContentAndTag(String content, String tag, Pageable pageable);
    PostEntity findById(long id);
}
