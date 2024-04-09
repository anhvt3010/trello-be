package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.CardDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.entity.Card;

import java.util.List;

public interface CardService {
    ServiceResult<CardDTO> findOne(String id);
    ServiceResult<List<CardDTO>> findByColumnId(String columnId);
    ServiceResult<CardDTO> save(CardDTO cardDTO);
    ServiceResult<CardDTO> update(CardDTO cardDTO);
    void delete(String id);
}
