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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String getIndex(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails,@PageableDefault(size=5) Pageable pageable){
        Page<Board> board = boardRepository.findAllByOrderByModifiedAtDesc(pageable);
        //board.getTotalElements(); // 전체데이터 건수

        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        // 현재 페이지 넘버 - 4 (4는 임의로 정한값)을 뺀값을 보여줄 페이지 값에 첫번째 값으로 지정
        // 현재 페이지 넘버 + 4 을 더한값을 보여줄 페이지에서 끝값으로 표시
        int startPage = Math.max(1, board.getPageable().getPageNumber() - 4); //그런데 음수가 나올수 있으니 max() 함수를 이용해 0보다 작은값은 나오지 않도록 만들어줌
        int endPage = Math.min(board.getTotalPages(),board.getPageable().getPageNumber() + 4); // 마찬가지로 최대페이지수를 초과하지않게 min()함수 걸어줌

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("board",board);
        return "index";
    }

    //게시글 작성 페이지
    @GetMapping("/api/board")
    public String getNotice( Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Board board = new Board();
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("board", board);
        return "board";
    }

    // 게시글 작성
    @PostMapping("/api/board")
    public String createNotice(@AuthenticationPrincipal UserDetailsImpl userDetails,@ModelAttribute BoardRequestDto requestDto){
        requestDto.setUser(userDetails.getUser());
        Board board = new Board(requestDto);
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
            model.addAttribute("user","null");
        }else{

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
    public String getEditBoard(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("board",board);
        return "editboard";
    }


    @PutMapping("/api/board/{id}/edit")
    public String updateBoard(@PathVariable Long id, @ModelAttribute BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        requestDto.setUser(userDetails.getUser());
        boardService.update(id, requestDto);
        return "redirect:/";

    }

    @DeleteMapping("/api/board/{id}/delete")
    public String deleteBoard(@PathVariable Long id){
        boardRepository.deleteById(id);
        return "redirect:/";
    }

}
