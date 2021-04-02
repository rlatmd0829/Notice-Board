package com.example.noticeboard.controller;

import com.example.noticeboard.dto.CommentRequestDto;
import com.example.noticeboard.models.Board;
import com.example.noticeboard.models.Comment;
import com.example.noticeboard.models.User;
import com.example.noticeboard.repository.BoardRepository;
import com.example.noticeboard.repository.CommentRepository;
import com.example.noticeboard.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @PostMapping("/api/board/{id}/comment")
    public String createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = new Comment(requestDto);
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        comment.setUser(userDetails.getUser());
        comment.setBoard(board);
        commentRepository.save(comment);
        return "redirect:/api/board/{id}";
    }

    @PutMapping("/api/board/{id}/comment/{commentId}")
    public String editComment(@PathVariable Long commentId, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        comment.setText(requestDto.getText());

        commentRepository.save(comment);
        return "redirect:/api/board/{id}";
    }

    @DeleteMapping("/api/board/{id}/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
        return "redirect:/api/board/{id}";
    }
}
