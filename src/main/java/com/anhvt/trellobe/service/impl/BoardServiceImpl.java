package com.anhvt.trellobe.service.impl;

import com.anhvt.trellobe.dto.BoardDTO;
import com.anhvt.trellobe.dto.CardDTO;
import com.anhvt.trellobe.dto.ColumnDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.entity.Board;
import com.anhvt.trellobe.entity.ColumnE;
import com.anhvt.trellobe.mapper.ColumnMapper;
import com.anhvt.trellobe.repository.BoardRepository;
import com.anhvt.trellobe.repository.CardRepository;
import com.anhvt.trellobe.repository.ColumnRepository;
import com.anhvt.trellobe.service.BoardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final CardRepository cardRepository;
    private final ColumnMapper columnMapper;
    private final ModelMapper mapper;

    @Override
    public ServiceResult<BoardDTO> findOne(String id) {
        log.debug("Request to get Board : {}", id);
        ServiceResult<BoardDTO> result = new ServiceResult<>();
        List<ColumnDTO> columnDTOS = new ArrayList<>();
        BoardDTO boardDTO;
        try {
            Optional<Board> board = boardRepository.findById(id);
            if (board.isPresent()){
                //Lấy tất cả Columns theo Board
                List<ColumnE> columns = columnRepository.findByBoardId(board.get().getId());

                //Lấy tất cả Column có dữ liệu Cards
                columns.forEach(column -> {
                    ColumnDTO columnDTO;
                    columnDTO = mapper.map(columnMapper.toDto(column), ColumnDTO.class);
                    columnDTO.setCards(cardRepository.findCardByColumnId(column.getId()).stream()
                            .map(c -> mapper.map(c, CardDTO.class))
                            .toList());
                    columnDTOS.add(columnDTO);
                });

                boardDTO = mapper.map(board.get(), BoardDTO.class);
                boardDTO.setColumns(columnDTOS);

                result.setStatus(HttpStatus.OK);
                result.setData(boardDTO);
            } else {
                result.setStatus(HttpStatus.NOT_FOUND);
                result.setMessage("board.id.not_found");
            }
        } catch (Exception e){
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("board.id.bad_request");
        }
        return result;
    }

    @Override
    public ServiceResult<List<BoardDTO>> findByUserId(String userId) {
        return null;
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
            Optional<Board> boardUpdate = boardRepository.findById(boardId);
            if (boardUpdate.isPresent()){
                boardUpdate.get().setColumnOrderIds(columnOrderIds);
                Board board = boardRepository.save(boardUpdate.get());

                result.setData(mapper.map(board, BoardDTO.class));
                result.setStatus(HttpStatus.OK);
                result.setMessage("column_order_ids.update.success");
            } else {
                result.setStatus(HttpStatus.NOT_FOUND);
                result.setMessage("board.id.not_found");
            }
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
}
