package com.anhvt.trellobe.mapper;

import com.anhvt.trellobe.dto.ColumnDTO;
import com.anhvt.trellobe.entity.ColumnE;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ColumnMapper implements EntityMapper<ColumnE, ColumnDTO>{
    @Override
    public ColumnE toEntity(ColumnDTO dto) {
        if(dto == null) return null;
        ColumnE columnE = ColumnE.builder()
                .id(dto.getId())
                .boardId(dto.getBoardId())
                .title(dto.getTitle())
                .cardOrderIds(dto.getCardOrderIds())
                .status(dto.getStatus())
                .build();
        columnE.setCreatedAt(dto.getCreatedAt());
        columnE.setUpdatedAt(dto.getUpdatedAt());

        return columnE;
    }

    @Override
    public ColumnDTO toDto(ColumnE entity) {
        if(entity == null){
            return null;
        }
        ColumnDTO columnDTO = ColumnDTO.builder()
                .id(entity.getId())
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .cardOrderIds(entity.getCardOrderIds())
                .cards(new ArrayList<>())
                .status(entity.getStatus())
                .build();
        columnDTO.setCreatedAt(entity.getCreatedAt());
        columnDTO.setUpdatedAt(entity.getUpdatedAt());
        return columnDTO;
    }

    @Override
    public List<ColumnE> toEntity(List<ColumnDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ColumnE> list = new ArrayList<>( dtoList.size() );
        for ( ColumnDTO columnDTO : dtoList ) {
            list.add( toEntity( columnDTO ) );
        }

        return list;
    }

    @Override
    public List<ColumnDTO> toDto(List<ColumnE> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ColumnDTO> list = new ArrayList<>( entityList.size() );
        for ( ColumnE column : entityList ) {
            list.add( toDto( column ) );
        }

        return list;
    }
}
