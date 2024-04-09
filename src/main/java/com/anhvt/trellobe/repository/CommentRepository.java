package com.anhvt.trellobe.repository;

import com.anhvt.trellobe.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, String> {
    List<Comment> findByCardId(String cardId);
}
