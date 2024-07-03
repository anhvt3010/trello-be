package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.BoardDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.UserDTO;

import java.util.List;

public interface BoardService {
    ServiceResult<BoardDTO> save(BoardDTO boardDTO);
    ServiceResult<BoardDTO> findOne(String id);
    ServiceResult<BoardDTO> findByUsername(String username);
    ServiceResult<BoardDTO> update(BoardDTO boardDTO);
    ServiceResult<BoardDTO> updateColumnOrder(String boardId, List<String> columnOrderIds);
    void delete(String id);
    BoardDTO generateBoard(UserDTO userDTO);


}
