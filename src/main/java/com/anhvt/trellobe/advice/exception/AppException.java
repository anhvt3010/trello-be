package com.anhvt.trellobe.advice.exception;

import com.anhvt.trellobe.advice.ErrorCode;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
public class NotFoundException extends RuntimeException {
    private ErrorCode errorCode;
}
