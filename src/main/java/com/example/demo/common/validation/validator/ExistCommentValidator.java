package com.example.demo.common.validation.validator;

import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.validation.annotation.ExistComment;
import com.example.demo.common.validation.annotation.ExistPost;
import com.example.demo.src.comment.CommentRepository;
import com.example.demo.src.post.PostRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistCommentValidator implements ConstraintValidator<ExistComment, Long> {

    private final CommentRepository commentRepository;

    @Override
    public void initialize(ExistComment constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long commentId, ConstraintValidatorContext context) {
        BaseResponseStatus errorStatus;

        boolean isValid;

        // null is invalid
        if (commentId == null) {
            errorStatus = BaseResponseStatus.COMMENT_ID_NULL;
            isValid = false;
        } else {
            errorStatus = BaseResponseStatus.COMMENT_NOT_FOUND;
            isValid = commentRepository.findById(commentId).isPresent();
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorStatus.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
