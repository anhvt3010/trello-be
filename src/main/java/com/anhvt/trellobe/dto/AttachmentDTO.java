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
public class AttachmentDTO {
    private String id;
    private String cardId;
    private String fileName;
    private String fileType;
    private String fileURL;
    private Timestamp createdAt;
}
