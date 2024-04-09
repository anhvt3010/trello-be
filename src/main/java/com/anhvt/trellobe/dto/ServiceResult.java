package com.anhvt.trellobe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResult<T> implements Serializable {
    private HttpStatus status;
    private String message;
    private T data;

}
