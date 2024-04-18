package com.anhvt.trellobe.advice;

import com.anhvt.trellobe.advice.exception.AppException;
import com.anhvt.trellobe.configuration.MessageConfig;
import com.anhvt.trellobe.dto.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler {
    private final MessageConfig messageConfig;
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(e -> errorMap.put(e.getField(), e.getDefaultMessage()));
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AppException.class)
    public ServiceResult<?> handleExisted(AppException ex){
        ErrorCode code = ex.getErrorCode();
        ServiceResult<?> result = new ServiceResult<>();
        result.setStatus(code.getStatus());
        result.setMessage(code.getMessage());
        return result;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ServiceResult<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ServiceResult<?> result = new ServiceResult<>();
        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintEx = (ConstraintViolationException) ex.getCause();
            String constraintName = constraintEx.getConstraintName();
            if (constraintName.contains("email")) {
                result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                result.setMessage(messageConfig.getMessage("email.exist"));
                return result;
            } else if (constraintName.contains("username")) {
                result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                result.setMessage(messageConfig.getMessage("username.exist"));
                return result;
            }
        }
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        result.setMessage(messageConfig.getMessage("unknown.error"));
        return result;
    }
}
