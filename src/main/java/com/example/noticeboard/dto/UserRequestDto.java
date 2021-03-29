package com.example.noticeboard.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp="\\w{4,8}", message="아이디를 4~8자로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String checkpw;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;

    private Long kakaoId;

}
