package com.example.demo.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberDTO {
    @NotBlank(message = "아이디를 확인해주세요.")
    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)(a-zA-Z.+\\d){6,12}", message = "아이디는 영어 대소문자, 특수문자, 숫자를 포함해서 6~!2자리입니다.")
    private String userid;
    @NotBlank(message = "비밀번호를 확인해주세요.")
    private String password;

    @NotBlank(message = "이메일을 확인해주세요")
    @Email
    private String email;
    @Length(min = 2, max = 5)
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 2~5글자, 한글로 입력해야합니다.")
    private String name;

    @NotNull(message = "나이를 입력해야 합니다.")
    @Min(value = 0, message = "나이는 최소 0 이상이어야 합니다.")
    @Max(value = 999, message = "나이는 최대 999 이하이어야 합니다.")
    private Integer age;

    private boolean check;
}
