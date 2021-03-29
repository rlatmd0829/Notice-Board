package com.example.noticeboard.service;

import com.example.noticeboard.dto.UserRequestDto;
import com.example.noticeboard.models.User;
import com.example.noticeboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void registerUser(UserRequestDto requestDto){
        String username = requestDto.getUsername();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username); // Optional을 쓰면 null을 받을 수 있다.
        if(found.isPresent()){ // found가 null이 아니면 true를 출력한다.
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // 패스워드
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        User user = new User(username, password, email);
        userRepository.save(user);
    }
}
