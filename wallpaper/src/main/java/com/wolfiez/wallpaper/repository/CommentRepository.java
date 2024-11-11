package com.wolfiez.wallpaper.repository;

import com.wolfiez.wallpaper.entity.Comment;
import com.wolfiez.wallpaper.entity.Pin;
import com.wolfiez.wallpaper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPin(Pin pin);
    List<Comment> findByUser(User user);

    @Query("SELECT c FROM Comment c WHERE c.pin = :pin ORDER BY c.createdAt DESC")
    List<Comment> findPinComments(@Param("pin") Pin pin, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.pin = :pin")
    long countPinComments(@Param("pin") Pin pin);

    @Query("SELECT c FROM Comment c WHERE c.content LIKE %:keyword%")
    List<Comment> searchComments(@Param("keyword") String keyword);
}