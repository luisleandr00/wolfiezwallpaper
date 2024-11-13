package com.wolfiez.wallpaper.service;

import com.wolfiez.wallpaper.entity.PinLikeStats;
import com.wolfiez.wallpaper.entity.UserPinStats;
import com.wolfiez.wallpaper.repository.StatsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StatsService {
    private final StatsRepository statsRepository;

    public StatsService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public long getTotalPins() {
        return statsRepository.getTotalPins();
    }

    public long getTotalUsers() {
        return statsRepository.getTotalUsers();
    }

    public long getTotalBoards() {
        return statsRepository.getTotalBoards();
    }

    public List<UserPinStats> getMostActiveUsers(int limit) {
        List<Object[]> results = statsRepository.getMostActiveUsers((Pageable) PageRequest.of(0, limit));
        return results.stream()
                .map(row -> new UserPinStats((Long) row[0], (String) row[1], (long) row[2]))
                .collect(Collectors.toList());
    }

    public List<PinLikeStats> getMostLikedPins(int limit) {
        List<Object[]> results = statsRepository.getMostLikedPins((Pageable) PageRequest.of(0, limit));
        return results.stream()
                .map(row -> new PinLikeStats((Long) row[0], (String) row[1], (long) row[2]))
                .collect(Collectors.toList());
    }

    public long getPinsCreatedSince(LocalDateTime startDate) {
        return statsRepository.getPinsCreatedSince(startDate);
    }
}