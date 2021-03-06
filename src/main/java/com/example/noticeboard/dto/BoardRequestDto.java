package com.example.noticeboard.dto;

import com.example.noticeboard.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private String title;
    private String text;
    private User user;
}
