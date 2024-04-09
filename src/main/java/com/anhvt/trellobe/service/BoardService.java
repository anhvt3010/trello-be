package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.BoardDTO;
import com.anhvt.trellobe.dto.ServiceResult;

import java.util.List;

public interface BoardService {
    ServiceResult<BoardDTO> findOne(String id);
    ServiceResult<List<BoardDTO>> findByUserId(String userId);
    ServiceResult<BoardDTO> update(BoardDTO boardDTO);
    ServiceResult<BoardDTO> updateColumnOrder(String boardId, List<String> columnOrderIds);
    void delete(String id);

}
