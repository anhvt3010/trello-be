package com.anhvt.trellobe.repository;

import com.anhvt.trellobe.entity.ColumnE;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepository extends JpaRepository<ColumnE, String> {
    List<ColumnE> findByBoardId(String boardId);
    @Modifying
    @Transactional
    @Query("UPDATE ColumnE c SET c.cardOrderIds = :cardOrderIds WHERE c.id = :columnId")
    void updateCardOrderIdsById(String columnId, List<String> cardOrderIds);
}
