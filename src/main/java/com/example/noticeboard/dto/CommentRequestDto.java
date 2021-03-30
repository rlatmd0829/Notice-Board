package com.example.noticeboard.dto;

import com.example.noticeboard.models.Board;
import com.example.noticeboard.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String text;
    private User user;
    private Board board;
}
