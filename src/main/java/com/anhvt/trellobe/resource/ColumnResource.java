package com.anhvt.trellobe.resource;

import com.anhvt.trellobe.dto.ColumnDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.request.CardMoveRequest;
import com.anhvt.trellobe.service.ColumnService;
import com.anhvt.trellobe.service.impl.ColumnServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/columns")
public class ColumnResource {
    private final Logger log = LoggerFactory.getLogger(ColumnResource.class);
    private final ColumnService columnService;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult<ColumnDTO>> findOne(@PathVariable("id") String id){
        ServiceResult<ColumnDTO> result = columnService.findOne(id);
        return new ResponseEntity<>(result, result.getStatus());
    }
    @GetMapping("/by-board/{boardId}")
    public ResponseEntity<ServiceResult<List<ColumnDTO>>> findByBoardId(@PathVariable("boardId") String boardId){
        ServiceResult<List<ColumnDTO>> result = columnService.findByBoardId(boardId);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult<ColumnDTO>> movingCardInColumn(@PathVariable("id") String columnId,
                                                                       @RequestBody ArrayList<String> cardOrderIDs){
        ServiceResult<ColumnDTO> result = columnService.movingCardInColumn(columnId, cardOrderIDs);
        return new ResponseEntity<>(result, result.getStatus());
    }
    @PutMapping("/moving-different-columns")
    public ResponseEntity<ServiceResult<?>> movingCardDifferentColumns(@RequestBody CardMoveRequest CardMoveRequest){
        ServiceResult<?> result = columnService.movingCardDifferentColumns(CardMoveRequest);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @PostMapping
    public ResponseEntity<ServiceResult<ColumnDTO>> save(@Valid @RequestBody ColumnDTO columnDTO) {
        ServiceResult<ColumnDTO> result = columnService.save(columnDTO);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult<?>> delete(@PathVariable("id") String id){
        ServiceResult<?> result = columnService.delete(id);
        return new ResponseEntity<>(result, result.getStatus());
    }

}
