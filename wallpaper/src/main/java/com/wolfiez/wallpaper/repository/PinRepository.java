package com.wolfiez.wallpaper.repository;

import com.wolfiez.wallpaper.entity.Pin;
import com.wolfiez.wallpaper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {
    List<Pin> findByUser(User user);

    @Query("SELECT p FROM Pin p WHERE p.title LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Pin> searchPins(@Param("keyword") String keyword);

    @Query("SELECT p FROM Pin p ORDER BY SIZE(p.likes) DESC")
    List<Pin> findMostLikedPins(Pageable pageable);

    @Query("SELECT p FROM Pin p ORDER BY p.createdAt DESC")
    List<Pin> findRecentPins(Pageable pageable);

    @Query("SELECT COUNT(l) > 0 FROM Pin p JOIN p.likes l WHERE p.id = :pinId AND l.id = :userId")
    boolean isLikedByUser(@Param("pinId") Long pinId, @Param("userId") Long userId);

    @Query("SELECT p FROM Pin p JOIN p.boards b WHERE b.id = :boardId")
    List<Pin> findByBoardId(@Param("boardId") Long boardId);
}
