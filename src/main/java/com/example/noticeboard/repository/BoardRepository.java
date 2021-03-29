package com.example.noticeboard.repository;

import com.example.noticeboard.models.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
