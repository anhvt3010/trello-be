package com.anhvt.trellobe.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class CardMoveRequest {
    private String currentCardId;
    private String prevColumnId;
    private List<String> prevCardOrderIds;
    private String nextColumnId;
    private List<String> nextCardOrderIds;
}
