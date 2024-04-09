package com.anhvt.trellobe.service.impl;

import com.anhvt.trellobe.dto.CommentDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.entity.Comment;
import com.anhvt.trellobe.repository.CommentRepository;
import com.anhvt.trellobe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;
    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Override
    public ServiceResult<List<CommentDTO>> findByCardId(String cardId) {
        log.debug("Request to get Comment by cardId : {}", cardId);
        ServiceResult<List<CommentDTO>> result = new ServiceResult<>();
        try {
            List<Comment> comments = commentRepository.findByCardId(cardId);
            if (comments.isEmpty()){
                result.setStatus(HttpStatus.NOT_FOUND);
                result.setMessage("comment.card_id.not_found");
            } else {
                result.setData(comments.stream()
                        .map(comment -> mapper.map(comment, CommentDTO.class))
                        .toList());
                result.setStatus(HttpStatus.OK);
            }
        } catch (Exception e) {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("comment.card_id.bad_request");
        }
        return result;
    }
}
