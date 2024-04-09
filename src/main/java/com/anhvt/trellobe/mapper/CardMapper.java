package com.anhvt.trellobe.mapper;

import com.anhvt.trellobe.dto.CardDTO;
import com.anhvt.trellobe.entity.Card;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardMapper implements EntityMapper<Card, CardDTO>{
    @Override
    public Card toEntity(CardDTO dto) {
        if(dto == null) return null;
        Card card = Card.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .attachmentIds(dto.getAttachmentIds())
                .cover(dto.getCover())
                .commentIds(dto.getCommentIds())
                .description(dto.getDescription())
                .memberIds(dto.getMemberIds())
                .columnId(dto.getColumnId())
//                .boardId(dto.getBoardId())
                .status(dto.getStatus())
                .build();
        card.setCreatedAt(dto.getCreatedAt());
        card.setUpdatedAt(dto.getUpdatedAt());
        return card;
    }

    @Override
    public CardDTO toDto(Card entity) {
        if(entity == null) return null;
        CardDTO cardDTO = CardDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .attachmentIds(entity.getAttachmentIds())
                .cover(entity.getCover())
                .commentIds(entity.getCommentIds())
                .description(entity.getDescription())
                .memberIds(entity.getMemberIds())
                .columnId(entity.getColumnId())
//                .boardId(entity.getBoardId())
                .status(entity.getStatus())
                .build();
        cardDTO.setCreatedAt(entity.getCreatedAt());
        cardDTO.setUpdatedAt(entity.getUpdatedAt());
        return cardDTO;
    }

    @Override
    public List<Card> toEntity(List<CardDTO> dtoList) {
        if ( dtoList == null ) return null;

        List<Card> list = new ArrayList<>( dtoList.size() );
        for ( CardDTO cardDTO : dtoList ) {
            list.add( toEntity( cardDTO ) );
        }

        return list;
    }

    @Override
    public List<CardDTO> toDto(List<Card> entityList) {
        if ( entityList == null ) return null;

        List<CardDTO> list = new ArrayList<>( entityList.size() );
        for ( Card card : entityList ) {
            list.add( toDto( card ) );
        }

        return list;
    }
}
