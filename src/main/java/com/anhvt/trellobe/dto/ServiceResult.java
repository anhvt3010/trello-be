package com.anhvt.trellobe.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceResult<T> implements Serializable {
    private HttpStatus status;
    private String message;
    private T data;

}
