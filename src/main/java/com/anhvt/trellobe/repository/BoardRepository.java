package com.anhvt.trellobe.repository;

import com.anhvt.trellobe.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, String> {
    @Query("SELECT b, c FROM Board b JOIN ColumnE c WHERE c.id IN :columnIds")
    List<Object[]> findBoardWithColumns(@Param("columnIds") List<Integer> columnIds);

}
