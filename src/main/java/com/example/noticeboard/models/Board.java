package com.example.noticeboard.models;

import com.example.noticeboard.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(nullable=false)
    private User user;



    public Board(BoardRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.text = requestDto.getText();
        this.user = requestDto.getUser();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.text = requestDto.getText();
        this.user = requestDto.getUser();
    }
}
