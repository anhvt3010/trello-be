package com.anhvt.trellobe.resource;

import com.anhvt.trellobe.dto.CommentDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comments")
public class CommentResource {
    private final CommentService commentService;

    @GetMapping("/by-card/{id}")
    public ResponseEntity<ServiceResult<List<CommentDTO>>> findByCardId(@PathVariable("id") String id){
        ServiceResult<List<CommentDTO>> result = commentService.findByCardId(id);
        return new ResponseEntity<>(result, result.getStatus());
    }
}
