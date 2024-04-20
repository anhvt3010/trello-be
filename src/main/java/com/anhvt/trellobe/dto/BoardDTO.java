package com.anhvt.trellobe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
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
@MappedSuperclass
public class BoardDTO {
    @JsonProperty("_id")
    private String id;
    private String username;
    private String title;
    private String type;
    private String description;
    private List<String> ownerIds;
    private List<String> memberIds;
    private List<String> columnOrderIds;
    private List<ColumnDTO> columns;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean status;
}
