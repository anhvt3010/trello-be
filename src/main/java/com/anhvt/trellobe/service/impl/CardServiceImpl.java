package com.anhvt.trellobe.service.impl;

import com.anhvt.trellobe.dto.CardDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.entity.Card;
import com.anhvt.trellobe.entity.ColumnE;
import com.anhvt.trellobe.mapper.CardMapper;
import com.anhvt.trellobe.repository.CardRepository;
import com.anhvt.trellobe.repository.ColumnRepository;
import com.anhvt.trellobe.service.CardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardServiceImpl implements CardService {
    Logger log = LoggerFactory.getLogger(CardServiceImpl.class);
    CardRepository cardRepository;
    ColumnRepository columnRepository;
    CardMapper cardMapper;


    @Override
    public ServiceResult<CardDTO> findOne(String id) {
        log.debug("Request to get Card : {}", id);
        ServiceResult<CardDTO> result = new ServiceResult<>();
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()){
            result.setStatus(HttpStatus.OK);
            result.setData(cardMapper.toDto(card.get()));
        } else {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("card.id.mot_found");
        }
        return result;
    }

    @Override
    public ServiceResult<List<CardDTO>> findByColumnId(String columnId) {
        log.debug("Request to get Card by column_id : {}", columnId);
        ServiceResult<List<CardDTO>> result = new ServiceResult<>();
        result.setData(cardMapper.toDto(cardRepository.findCardByColumnId(columnId)));
        result.setStatus(HttpStatus.OK);
        return result;
    }

    @Override
    public ServiceResult<CardDTO> save(CardDTO cardDTO) {
        log.debug("Request to save Card : {}", cardDTO);
        ServiceResult<CardDTO> result = new ServiceResult<>();
        try{
            if(columnRepository.existsById(cardDTO.getColumnId())) {
                Card card = cardRepository.save(cardMapper.toEntity(cardDTO));

                ColumnE column = columnRepository.findById(cardDTO.getColumnId())
                        .orElseThrow(() -> new RuntimeException("Column not found"));
                List<String> cardOrderIds = column.getCardOrderIds();
                if (cardOrderIds == null) {
                    cardOrderIds = new ArrayList<>();
                }
                cardOrderIds.add(card.getId());
                column.setCardOrderIds(cardOrderIds);
                columnRepository.save(column);

                result.setStatus(HttpStatus.CREATED);
                result.setData(cardMapper.toDto(card));
                result.setMessage("card.create.success");
            } else {
                result.setStatus(HttpStatus.NOT_FOUND);
                result.setMessage("column.id.not_found");
            }
        } catch (Exception e){
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("card.create.failed");
        }
        return result;
    }

    @Override
    public ServiceResult<CardDTO> update(CardDTO cardDTO) {
        log.debug("Request to update Card : {}", cardDTO);

        return null;
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Card : {}", id);
        cardRepository.deleteById(id);
    }
}
