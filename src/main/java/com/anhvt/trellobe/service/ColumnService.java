package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.ColumnDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.request.CardMoveRequest;

import java.util.List;

public interface ColumnService {
    ServiceResult<ColumnDTO> findOne(String id);
    ServiceResult<List<ColumnDTO>> findByBoardId(String boardId);
    ServiceResult<ColumnDTO> save(ColumnDTO columnDTO);
    ServiceResult<ColumnDTO> update(ColumnDTO columnDTO);
    ServiceResult<ColumnDTO> movingCardInColumn(String columnId, List<String> cardOrderIds);
    ServiceResult<?> movingCardDifferentColumns(CardMoveRequest cardMoveRequest);
    ServiceResult<?> delete(String id);

}
