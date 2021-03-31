package com.example.noticeboard.controller;

import com.example.noticeboard.models.Board;
import com.example.noticeboard.models.Comment;
import com.example.noticeboard.models.User;
import com.example.noticeboard.repository.BoardRepository;
import com.example.noticeboard.dto.BoardRequestDto;
import com.example.noticeboard.repository.CommentRepository;
import com.example.noticeboard.repository.UserRepository;
import com.example.noticeboard.security.UserDetailsImpl;
import com.example.noticeboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@ResponseBody 이거 추가하면 타임리프 작동을 안한다

public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    //게시글 전체 조회
//    @GetMapping("/api/index")
//    public String getBoard(Model model){
//        List<Board> board = boardRepository.findAll();
//        model.addAttribute("board",board);
//        return "index";
//    }

//    @GetMapping("/")
//    public String getIndex(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        List<Board> board = boardRepository.findAll();
//        model.addAttribute("board",board);
//        model.addAttribute("username",userDetails.getUsername());
//        return "index";
//    }

    @GetMapping("/")
    public String getIndex(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<Board> board1 = boardRepository.findAll();
        System.out.println(userDetails);
        model.addAttribute("user",userDetails);
        model.addAttribute("board",board1);
        return "index";
    }

    //게시글 작성 페이지
    @GetMapping("/api/board")
    public String getNotice( Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Board board = new Board();
        model.addAttribute("user",userDetails);
        model.addAttribute("board", board);
        return "board";
    }

    // 게시글 작성
    @PostMapping("/api/board")
    public String createNotice(@AuthenticationPrincipal UserDetailsImpl userDetails,@ModelAttribute BoardRequestDto requestDto){
        Board board = new Board(requestDto);
        board.setUser(userDetails.getUser());
        boardRepository.save(board);
        return "redirect:/";
    }



    //게시글 한개 조회페이지
    @GetMapping("/api/board/{id}")
    public String getOneBoard(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<Comment> comment = commentRepository.findByBoardIdOrderByModifiedAtDesc(id);
        if(userDetails == null){
            model.addAttribute("user",userDetails);
        }else{
            System.out.println(userDetails.getUser().getUsername());
            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("editcomment",new Comment());
        model.addAttribute("postcomment",new Comment());
        model.addAttribute("comment", comment);
        model.addAttribute("board",board);
//        comment.get(0).getUser().getUsername()
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
