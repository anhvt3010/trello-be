package com.anhvt.trellobe.util.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusUser {
    IS_ACTIVE((byte)1, "user.is_active"),
    UN_ACTIVE((byte)0, "user.un_active");

    private final byte isActive;
    private final String message;
}
