package com.example.demo.common.validation.validator;

import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.validation.annotation.ExistPost;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.post.PostRepository;
import com.example.demo.src.user.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistPostValidator implements ConstraintValidator<ExistPost, Long> {

    private final PostRepository postRepository;

    @Override
    public void initialize(ExistPost constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long postId, ConstraintValidatorContext context) {
        BaseResponseStatus errorStatus;

        boolean isValid;

        // null is invalid
        if (postId == null) {
            errorStatus = BaseResponseStatus.POST_ID_NULL;
            isValid = false;
        } else {
            errorStatus = BaseResponseStatus.POST_NOT_FOUND;
            isValid = postRepository.findById(postId).isPresent();
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorStatus.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
