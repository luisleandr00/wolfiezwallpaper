package com.wolfiez.wallpaper.repository;

import com.wolfiez.wallpaper.entity.Pin;
import com.wolfiez.wallpaper.entity.UserPinStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository {
    @Query("SELECT COUNT(p) FROM Pin p")
    long getTotalPins();

    @Query("SELECT COUNT(u) FROM User u")
    long getTotalUsers();

    @Query("SELECT COUNT(b) FROM Board b")
    long getTotalBoards();

    @Query("SELECT u.id, u.name, COUNT(p) as pinCount " +
            "FROM User u LEFT JOIN u.pins p " +
            "GROUP BY u.id, u.name " +
            "ORDER BY pinCount DESC")
    List<Object[]> getMostActiveUsers(Pageable pageable);

    @Query("SELECT p.id, p.title, SIZE(p.likes) as likeCount " +
            "FROM Pin p " +
            "ORDER BY likeCount DESC")
    List<Object[]> getMostLikedPins(Pageable pageable);

    @Query("SELECT COUNT(p) FROM Pin p WHERE p.createdAt >= :startDate")
    long getPinsCreatedSince(@Param("startDate") LocalDateTime startDate);
}