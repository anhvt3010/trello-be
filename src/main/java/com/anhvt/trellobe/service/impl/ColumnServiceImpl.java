package com.anhvt.trellobe.service.impl;

import com.anhvt.trellobe.dto.CardDTO;
import com.anhvt.trellobe.dto.ColumnDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.request.CardMoveRequest;
import com.anhvt.trellobe.entity.Board;
import com.anhvt.trellobe.entity.Card;
import com.anhvt.trellobe.entity.ColumnE;
import com.anhvt.trellobe.mapper.ColumnMapper;
import com.anhvt.trellobe.repository.BoardRepository;
import com.anhvt.trellobe.repository.CardRepository;
import com.anhvt.trellobe.repository.ColumnRepository;
import com.anhvt.trellobe.service.ColumnService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService{
    private final Logger log = LoggerFactory.getLogger(ColumnServiceImpl.class);
    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;
    private final ColumnMapper columnMapper;
    private final ModelMapper mapper;

    @Override
    public ServiceResult<ColumnDTO> findOne(String id) {
        log.debug("Request to get Column : {}", id);
        ServiceResult<ColumnDTO> result = new ServiceResult<>();
        try {
            Optional<ColumnE> column = columnRepository.findById(id);
            if (column.isPresent()){
                result.setStatus(HttpStatus.OK);
                result.setData(mapper.map(column.get(), ColumnDTO.class));
            } else {
                result.setStatus(HttpStatus.NOT_FOUND);
                result.setMessage("column.id.not_found");
            }
        } catch (Exception e){
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("column.id.bad_request");
        }
        return result;
    }

    @Override
    public ServiceResult<List<ColumnDTO>> findByBoardId(String boardId) {
        log.debug("Request to get Column by board_id : {}", boardId);
        ServiceResult<List<ColumnDTO>> result = new ServiceResult<>();
        try {
            List<ColumnE> columnEs = columnRepository.findByBoardId(boardId);
            if(columnEs.isEmpty()){
                result.setStatus(HttpStatus.NOT_FOUND);
                result.setMessage("columns.board_id.not_found");
            } else {
                result.setData(columnEs.stream()
                        .map(column -> mapper.map(column, ColumnDTO.class))
                        .toList());
                result.setStatus(HttpStatus.OK);
            }
        } catch (Exception e){
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("columns.board_id.bad_request");
        }
        return result;
    }

    @Override
    public ServiceResult<ColumnDTO> save(ColumnDTO columnDTO) {
        log.debug("Request to save Column : {}", columnDTO);
        ServiceResult<ColumnDTO> result = new ServiceResult<>();
        try {
            if(boardRepository.existsById(columnDTO.getBoardId())){
                ColumnE column = columnRepository.save(mapper.map(columnDTO, ColumnE.class));

                Board board = boardRepository.findById(columnDTO.getBoardId()).orElseThrow(() -> new RuntimeException("Board not found"));

                List<String> columnOrderIds = board.getColumnOrderIds();
                if (columnOrderIds == null) {
                    columnOrderIds = new ArrayList<>();
                }
                columnOrderIds.add(column.getId());
                board.setColumnOrderIds(columnOrderIds);

                boardRepository.save(board);

                result.setStatus(HttpStatus.CREATED);
                result.setData(columnMapper.toDto(column));
                result.setMessage("column.create.success");
            } else {
                result.setStatus(HttpStatus.NOT_FOUND);
                result.setMessage("board.id.not_found");
            }
        } catch (Exception e){
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("column.create.failed");
        }
        return result;
    }

    @Override
    public ServiceResult<ColumnDTO> update(ColumnDTO columnDTO) {
        log.debug("Request to update Column : {}", columnDTO);

        return null;
    }

    @Override
    public ServiceResult<ColumnDTO> movingCardInColumn(String columnId, List<String> cardOrderIds) {
        log.debug("Request to update Card order ids in Column : {}", cardOrderIds);
        ServiceResult<ColumnDTO> result = new ServiceResult<>();
        try {
            columnRepository.findById(columnId)
                    .orElseThrow(() -> new EntityNotFoundException("Column with id " + columnId + " not found"));

            columnRepository.updateCardOrderIdsById(columnId, cardOrderIds);

            result.setStatus(HttpStatus.OK);
            result.setMessage("card_order_ids.update.success");
        } catch (EntityNotFoundException e) {
            log.error("Column not found: {}", e.getMessage());
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("column.id.not_found");
        } catch (Exception e) {
            log.error("Error updating card order ids: {}", e.getMessage());
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("column.update.bad_request");
        }
        return result;
    }

    @Override
    public ServiceResult<?> movingCardDifferentColumns(CardMoveRequest cardMoveRequest) {
        log.debug("Request to moving Card different Column : {}", cardMoveRequest);
        ServiceResult<List<ColumnE>> result = new ServiceResult<>();
        try {
            List<ColumnE> columns = columnRepository.findAllById(Arrays.asList(cardMoveRequest.getPrevColumnId(), cardMoveRequest.getNextColumnId()));
            if (columns.size() != 2) {
                throw new EntityNotFoundException("One or both columns not found");
            }

            cardRepository.updateColumnId(cardMoveRequest.getCurrentCardId(), cardMoveRequest.getNextColumnId());
            columnRepository.updateCardOrderIdsById(cardMoveRequest.getPrevColumnId(), cardMoveRequest.getPrevCardOrderIds());
            columnRepository.updateCardOrderIdsById(cardMoveRequest.getNextColumnId(), cardMoveRequest.getNextCardOrderIds());

            result.setStatus(HttpStatus.OK);
        }catch (Exception e) {
            log.error("Unexpected error during card movement: ", e);
            result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            result.setMessage("move_card.internal_error");
        }
        return result;
    }

    @Override
    public ServiceResult<?> delete(String id) {
        log.debug("Request to delete Column : {}", id);
        ServiceResult<?> result = new ServiceResult<>();
        try {
            ColumnE column = columnRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Column not found"));
//          // Xoa columnId trong columnOrderIds
            Board board = boardRepository.findById(column.getBoardId())
                    .orElseThrow(() -> new RuntimeException("Board not found"));
            List<String> columnOrderIds = board.getColumnOrderIds();
            if (columnOrderIds == null) {
                columnOrderIds = new ArrayList<>();
            }
            columnOrderIds.remove(column.getId());
            board.setColumnOrderIds(columnOrderIds);
            boardRepository.save(board);

            // Xoa Column
            columnRepository.deleteById(id);

            // Xoa Cards trong column
            cardRepository.deleteByColumnId(id);

            result.setMessage("delete.column.success");
            result.setStatus(HttpStatus.OK);
        } catch (Exception e){
            result.setMessage("delete.column.bad_request");
            result.setStatus(HttpStatus.BAD_REQUEST);
        }
        return  result;
    }
}
