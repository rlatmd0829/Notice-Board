package com.example.noticeboard.controller;

import com.example.noticeboard.models.Board;
import com.example.noticeboard.repository.BoardRepository;
import com.example.noticeboard.dto.BoardRequestDto;
import com.example.noticeboard.security.UserDetailsImpl;
import com.example.noticeboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@ResponseBody 이거 추가하면 타임리프 작동을 안한다
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    //게시글 전체 조회
    @GetMapping("/api/index")
    public String getBoard(Model model){
        List<Board> board = boardRepository.findAll();
        model.addAttribute("board",board);
        return "index";
    }

//    @GetMapping("/")
//    public String getIndex(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        List<Board> board = boardRepository.findAll();
//        model.addAttribute("board",board);
//        model.addAttribute("username",userDetails.getUsername());
//        return "index";
//    }

    @GetMapping("/")
    public String getIndex(Model model){
        List<Board> board = boardRepository.findAll();
        model.addAttribute("board",board);
        return "index";
    }

    //게시글 작성 페이지
    @GetMapping("/api/board")
    public String getNotice(Model model){
        model.addAttribute("board", new BoardRequestDto());
        return "board";
    }

    // 게시글 작성
    @PostMapping("/api/board")
    public String createNotice(@ModelAttribute BoardRequestDto requestDto){
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return "redirect:/";
    }



    //게시글 한개 조회페이지
    @GetMapping("/api/board/{id}")
    public String getOneBoard(@PathVariable Long id, Model model){
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")

        );
        model.addAttribute("board",board);
        return "/detailboard";
    }


    @GetMapping("/api/board/{id}/edit")
    public String getEditBoard(@PathVariable Long id, Model model){
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")

        );
        model.addAttribute("board",board);
        return "editboard";
    }


    @PutMapping("/api/board/{id}/edit")
    public String updateBoard(@PathVariable Long id, @ModelAttribute BoardRequestDto requestDto){
        boardService.update(id, requestDto);
        return "redirect:/";

    }

    @DeleteMapping("/api/board/{id}/delete")
    public String deleteBoard(@PathVariable Long id){
        boardRepository.deleteById(id);
        return "redirect:/";
    }

}
