package com.anhvt.trellobe.mapper;

import com.anhvt.trellobe.dto.BoardDTO;
import com.anhvt.trellobe.entity.Board;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BoardMapper implements EntityMapper<Board, BoardDTO>{
    @Override
    public Board toEntity(BoardDTO dto) {
        if(dto == null) return null;
        Board board = Board.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .type(dto.getType())
                .status(dto.getStatus())
                .username(dto.getUsername())
                .memberIds(dto.getMemberIds())
                .ownerIds(dto.getOwnerIds())
                .columnOrderIds(dto.getColumnOrderIds())
                .build();
        board.setCreatedAt(dto.getCreatedAt());
        board.setUpdatedAt(dto.getUpdatedAt());
        return board;
    }

    @Override
    public BoardDTO toDto(Board entity) {
        if(entity == null) return null;
        BoardDTO boardDTO = BoardDTO.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .type(entity.getType())
                .status(entity.getStatus())
                .username(entity.getUsername())
                .memberIds(entity.getMemberIds())
                .ownerIds(entity.getOwnerIds())
                .columnOrderIds(entity.getColumnOrderIds())
                .build();
        boardDTO.setCreatedAt(entity.getCreatedAt());
        boardDTO.setUpdatedAt(entity.getUpdatedAt());
        return boardDTO;
    }

    @Override
    public List<Board> toEntity(List<BoardDTO> dtoList) {
        if ( dtoList == null ) return null;
        return null;
    }

    @Override
    public List<BoardDTO> toDto(List<Board> entityList) {
        if ( entityList == null ) return null;
        return null;
    }
}
