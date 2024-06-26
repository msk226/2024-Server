package com.example.demo.common.exceptions;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.response.BaseResponseStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> BaseExceptionHandle(BaseException exception) {
        log.warn("BaseException. error message: {}", exception.getMessage());
        return new BaseResponse<>(exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<BaseResponseStatus> ExceptionHandle(Exception exception) {
        log.error("Exception has occured. ", exception);
        return new BaseResponse<>(BaseResponseStatus.UNEXPECTED_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<BaseResponseStatus> MethodArgumentNotValidExceptionHandle(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.toList());

        String errorMessage = String.join(", ", errors);
        log.warn("MethodArgumentNotValidException. error message: {}", errorMessage);
        return new BaseResponse<>(errorMessage);
    }
}
