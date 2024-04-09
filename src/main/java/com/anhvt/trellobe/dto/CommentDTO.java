package com.anhvt.trellobe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private String id;
    private String userId;
    private String cardId;
    private String userEmail;
    private String userAvatar;
    private String userDisplayName;
    private String content;
    private Timestamp createdAt;
}
