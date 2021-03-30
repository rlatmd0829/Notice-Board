package com.example.noticeboard.repository;

import com.example.noticeboard.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBoardIdOrderByModifiedAtDesc(Long id);
}
