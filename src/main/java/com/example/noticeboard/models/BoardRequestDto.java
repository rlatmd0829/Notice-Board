package com.example.noticeboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private String title;
    private String name;
    private String text;
}
