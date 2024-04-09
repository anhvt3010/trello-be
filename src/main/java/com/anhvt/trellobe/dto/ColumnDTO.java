package com.anhvt.trellobe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnDTO {
    @JsonProperty("_id")
    private String id;

    @NotEmpty(message = "Board cannot be null")
    private String boardId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, min = 3 ,message = "Title range from 3 to 255 characters")
    private String title;
    private List<String> cardOrderIds;
    private List<CardDTO> cards;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean status;
}
