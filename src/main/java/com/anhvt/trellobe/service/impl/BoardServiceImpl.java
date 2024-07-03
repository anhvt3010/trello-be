package com.anhvt.trellobe.service.impl;

import com.anhvt.trellobe.advice.ErrorCode;
import com.anhvt.trellobe.advice.exception.AppException;
import com.anhvt.trellobe.dto.*;
import com.anhvt.trellobe.entity.Board;
import com.anhvt.trellobe.entity.ColumnE;
import com.anhvt.trellobe.mapper.BoardMapper;
import com.anhvt.trellobe.mapper.ColumnMapper;
import com.anhvt.trellobe.repository.BoardRepository;
import com.anhvt.trellobe.repository.CardRepository;
import com.anhvt.trellobe.repository.ColumnRepository;
import com.anhvt.trellobe.service.BoardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BoardServiceImpl implements BoardService {

    BoardRepository boardRepository;
    ColumnRepository columnRepository;
    CardRepository cardRepository;
    BoardMapper boardMapper;
    ColumnMapper columnMapper;
    ModelMapper mapper;

    @Override
    public ServiceResult<BoardDTO> save(BoardDTO boardDTO) {
        log.debug("Request to save Board : {}", boardDTO);
        ServiceResult<BoardDTO> result = new ServiceResult<>();
        try {
            Board board = boardRepository.save(boardMapper.toEntity(boardDTO));
            result.setData(mapper.map(board, BoardDTO.class));
            result.setStatus(HttpStatus.CREATED);
        } catch (Exception e){
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("board.create.bad_request");
        }
        return result;
    }
    @Override
    public BoardDTO generateBoard(UserDTO userDTO){
        return BoardDTO.builder()
                .username(userDTO.getUsername())
                .title("Board of " + userDTO.getDisplayName())
                .type("public")
                .description("")
                .ownerIds(new ArrayList<>())
                .memberIds(new ArrayList<>())
                .columnOrderIds(new ArrayList<>())
                .status(true)
                .build();
    }

    @Override
    public ServiceResult<BoardDTO> findOne(String id) {
        log.debug("Request to get Board : {}", id);
        ServiceResult<BoardDTO> result = new ServiceResult<>();
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOARD_NOT_FOUND));
        result.setStatus(HttpStatus.OK);
        result.setData(this.getBoardData(board));
        return result;
    }

    @Override
    public ServiceResult<BoardDTO> findByUsername(String username) {
        log.debug("Request to get Board by userId: {}", username);
        ServiceResult<BoardDTO> result = new ServiceResult<>();
        Board board = boardRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.BOARD_NOT_FOUND));
        result.setStatus(HttpStatus.OK);
        result.setData(this.getBoardData(board));
        return result;
    }

    @Override
    public ServiceResult<BoardDTO> update(BoardDTO boardDTO) {
        return null;
    }

    @Override
    public ServiceResult<BoardDTO> updateColumnOrder(String boardId, List<String> columnOrderIds) {
        log.debug("Request to update Board Column order ids: {}", columnOrderIds);
        ServiceResult<BoardDTO> result = new ServiceResult<>();
        try {
            Board boardUpdate = boardRepository.findById(boardId)
                    .orElseThrow(() -> new AppException(ErrorCode.BOARD_NOT_FOUND));

            boardUpdate.setColumnOrderIds(columnOrderIds);
            Board board = boardRepository.save(boardUpdate);

            result.setData(mapper.map(board, BoardDTO.class));
            result.setStatus(HttpStatus.OK);
            result.setMessage("column_order_ids.update.success");
        } catch (Exception e){
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("board.id.bad_request");
        }
        return result;
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Board : {}", id);
        boardRepository.deleteById(id);
    }

    private BoardDTO getBoardData(Board board){
        List<ColumnDTO> columnDTOS = new ArrayList<>();
        BoardDTO boardDTO;
        //Lấy tất cả Columns theo Board
        List<ColumnE> columns = columnRepository.findByBoardId(board.getId());

        //Lấy tất cả Column có dữ liệu Cards
        columns.forEach(column -> {
            ColumnDTO columnDTO;
            columnDTO = mapper.map(columnMapper.toDto(column), ColumnDTO.class);
            columnDTO.setCards(cardRepository.findCardByColumnId(column.getId()).stream()
                    .map(c -> mapper.map(c, CardDTO.class))
                    .toList());
            columnDTOS.add(columnDTO);
        });

        boardDTO = mapper.map(board, BoardDTO.class);
        boardDTO.setColumns(columnDTOS);

        return boardDTO;
    }
}
