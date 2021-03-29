package com.example.noticeboard.models;

import com.example.noticeboard.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter

@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String title;


    private String name;


    private String text;



    public Board(BoardRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.text = requestDto.getText();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.text = requestDto.getText();
    }
}
