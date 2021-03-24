package com.example.noticeboard.controller;

import com.example.noticeboard.models.Board;
import com.example.noticeboard.models.BoardRepository;
import com.example.noticeboard.models.BoardRequestDto;
import com.example.noticeboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@ResponseBody 이거 추가하면 타임리프 작동을 안한다
@RequiredArgsConstructor
public class noticeboardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    //게시글 작성 페이지
    @GetMapping("/api/notice")
    public String getNotice(Model model){
        model.addAttribute("board", new BoardRequestDto());
        return "/noticeboard";
    }

    // 게시글 작성
    @PostMapping("/api/notice")
    public String createNotice(@ModelAttribute BoardRequestDto requestDto){
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return "redirect:/api/boards";
    }

    //게시글 전체 조회
    @GetMapping("/api/boards")
    public String getBoard(Model model){
        List<Board> board = boardRepository.findAll();
        model.addAttribute("board",board);
        return "/timeline";
    }

    //게시글 한개 조회페이지
    @GetMapping("/api/boards/{id}")
    public String getOneBoard(@PathVariable Long id, Model model){
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")

        );
        model.addAttribute("board",board);
        return "/detailboard";
    }


    @DeleteMapping("/api/boards/{id}")
    public Long deleteBoard(@PathVariable Long id){
        boardRepository.deleteById(id);
        return id;
    }

    @PutMapping("/api/boards/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        return boardService.update(id, requestDto);
    }
}
