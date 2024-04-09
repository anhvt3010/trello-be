package com.anhvt.trellobe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.Mapping;

import java.sql.Timestamp;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardDTO {
    @JsonProperty("_id")
    String id;
//    String boardId;

    @NotEmpty(message = "Column id cannot be null")
    String columnId;

    @JsonProperty("attachments")
    List<String> attachmentIds;

    @JsonProperty("comments")
    List<String> commentIds;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, min = 3 ,message = "Title range from 3 to 255 characters")
    String title;

    String cover;
    String description;
    List<String> memberIds;
    Timestamp createdAt;
    Timestamp updatedAt;
    Boolean status;
}
