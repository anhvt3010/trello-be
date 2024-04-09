package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.CommentDTO;
import com.anhvt.trellobe.dto.ServiceResult;

import java.util.List;

public interface CommentService {
    ServiceResult<List<CommentDTO>> findByCardId(String cardId);
}
