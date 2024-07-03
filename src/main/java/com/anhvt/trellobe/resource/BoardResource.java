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

    @PostMapping
    public ResponseEntity<ServiceResult<BoardDTO>> save(@RequestBody BoardDTO boardDTO){
        ServiceResult<BoardDTO> result = boardService.save(boardDTO);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult<BoardDTO>> findOne(@PathVariable("id") String id){
        ServiceResult<BoardDTO> result = boardService.findOne(id);
        return new ResponseEntity<>(result, result.getStatus());
    }
    @GetMapping("/by-user/{username}")
    public ResponseEntity<ServiceResult<BoardDTO>> findByUsername(@PathVariable("username") String username){
        ServiceResult<BoardDTO> result = boardService.findByUsername(username);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult<BoardDTO>> updateColumnOderIds(@PathVariable("id") String id,
                                                                       @RequestBody String[] columnOrderIds)
    {
        ServiceResult<BoardDTO> result = boardService.updateColumnOrder(id, Arrays.stream(columnOrderIds).toList());
        return new ResponseEntity<>(result, result.getStatus());
    }

}
