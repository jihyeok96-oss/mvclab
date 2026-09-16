package com.example.mvclab.member.validation;

import com.example.mvclab.member.dto.MemberForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, MemberForm> {

    @Override
    public boolean isValid(MemberForm value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value.getPassword() == null || value.getPasswordConfirm() == null) {
            return true;
        }

        return value.getPassword().equals(value.getPasswordConfirm());
    }
}
