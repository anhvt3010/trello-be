package com.anhvt.trellobe.repository;

import com.anhvt.trellobe.entity.Card;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository  extends JpaRepository<Card, String> {
    List<Card> findCardByColumnId(String columnId);
    @Modifying
    @Transactional
    @Query("UPDATE Card c SET c.columnId = :columnId WHERE c.id = :cardId")
    void updateColumnId(String cardId, String columnId);

    Boolean existsByColumnId(String columnId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Card c WHERE c.columnId = :columnId")
    void deleteByColumnId(String columnId);
}
