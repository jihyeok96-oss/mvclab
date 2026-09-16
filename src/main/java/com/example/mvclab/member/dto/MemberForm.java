package com.example.mvclab.member.dto;
//폼 검증용

import com.example.mvclab.member.validation.PasswordMatch;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@PasswordMatch
public class MemberForm {

    private Long id;

    @NotNull(message = "아이디는 null일 수 없습니다.")
    @NotEmpty(message = "아이디는 비어 있을 수 없습니다.")
    @NotBlank(message = "아이디를 입력해주세요")
    @Size(
            min = 2,
            max = 20,
            message = "아이디는 2 ~ 20 자여야 합니다."
    )
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(
            min = 4, max = 20,
            message = "비밀번호는 4자 이상 20자 이하로 작성해주십시오."
    )
    @Pattern(
            regexp = "^[a-zA-z0-9]+$",
            message = "비밀번호는 영문과 숫자만 사용할 수 있습니다."
    )
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank(message = "이메일은 필수 입력항목입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Min(value = 19, message = "미성년자는 가입할 수 없습니다.")
    @Max(value = 150, message = "문의주시면 다시 가입 가능하게 조치하겠습니다. 010-xxxx-xxxx")
    @Range(
            min = 19, max = 150, message = "가입 가능 연령은 19세 ~ 150세 입니다."
    )
    @Positive(message = "나이는 양수여야 합니다.")
    private Integer age;

    @AssertTrue(message = "약관에 동의해주세요.")
    private Boolean agree;

    public boolean isAgreed() {
        return Boolean.TRUE.equals(agree);
    }
}
