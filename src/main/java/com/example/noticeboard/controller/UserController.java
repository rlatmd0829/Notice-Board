package com.example.noticeboard.controller;

import com.example.noticeboard.dto.UserRequestDto;
import com.example.noticeboard.models.User;
import com.example.noticeboard.repository.UserRepository;
import com.example.noticeboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup(Model model) {
        model.addAttribute("requestDto", new UserRequestDto());
        return "signup";
    }

    @PostMapping("/user/signup")
    public String registerUser(@Valid @ModelAttribute UserRequestDto requestDto, BindingResult result, Model model){
        if(result.hasErrors()){
            return "signup";
        }
        userService.registerUser(requestDto);
        return "redirect:/user/login";

    }
}