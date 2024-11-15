package com.wolfiez.wallpaper.repository;

import com.wolfiez.wallpaper.entity.Comment;
import com.wolfiez.wallpaper.entity.Pin;
import com.wolfiez.wallpaper.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPin(Pin pin);
    List<Comment> findByUser(User user);


    @Query("SELECT c FROM Comment c WHERE c.pin = :pin ORDER BY c.createdAt DESC")
    Optional<Comment> findPinComments(@Param("pin") Pin pin, PageRequest pageRequest);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.pin = :pin")
    long countPinComments(@Param("pin") Pin pin);

    @Query("SELECT c FROM Comment c WHERE c.content LIKE %:keyword%")
    List<Comment> searchComments(@Param("keyword") String keyword);
}