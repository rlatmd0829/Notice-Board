package com.example.noticeboard.repository;

import com.example.noticeboard.models.Board;
import com.example.noticeboard.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
}
