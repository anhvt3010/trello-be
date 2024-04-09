package com.anhvt.trellobe.resource;

import com.anhvt.trellobe.dto.BoardDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/boards")
public class BoardResource {
    private final BoardService boardService;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult<BoardDTO>> findOne(@PathVariable("id") String id){
        ServiceResult<BoardDTO> result = boardService.findOne(id);
        return new ResponseEntity<>(result, result.getStatus());
    }
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<ServiceResult<BoardDTO>> findByUserId(@PathVariable("userId") Integer id){
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult<BoardDTO>> updateColumnOderIds(@PathVariable("id") String id,
                                                                       @RequestBody String[] columnOrderIds)
    {
        ServiceResult<BoardDTO> result = boardService.updateColumnOrder(id, Arrays.stream(columnOrderIds).toList());
        return new ResponseEntity<>(result, result.getStatus());
    }

}
