package com.wolfiez.wallpaper.controller;

import com.wolfiez.wallpaper.entity.PinLikeStats;
import com.wolfiez.wallpaper.entity.UserPinStats;
import com.wolfiez.wallpaper.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/total-pins")
    public ResponseEntity<Long> getTotalPins() {
        long totalPins = statsService.getTotalPins();
        return new ResponseEntity<>(totalPins, HttpStatus.OK);
    }

    @GetMapping("/total-users")
    public ResponseEntity<Long> getTotalUsers() {
        long totalUsers = statsService.getTotalUsers();
        return new ResponseEntity<>(totalUsers, HttpStatus.OK);
    }

    @GetMapping("/total-boards")
    public ResponseEntity<Long> getTotalBoards() {
        long totalBoards = statsService.getTotalBoards();
        return new ResponseEntity<>(totalBoards, HttpStatus.OK);
    }

    @GetMapping("/most-active-users")
    public ResponseEntity<List<UserPinStats>> getMostActiveUsers(@RequestParam(defaultValue = "10") int limit) {
        List<UserPinStats> mostActiveUsers = statsService.getMostActiveUsers(limit);
        return new ResponseEntity<>(mostActiveUsers, HttpStatus.OK);
    }

    @GetMapping("/most-liked-pins")
    public ResponseEntity<List<PinLikeStats>> getMostLikedPins(@RequestParam(defaultValue = "10") int limit) {
        List<PinLikeStats> mostLikedPins = statsService.getMostLikedPins(limit);
        return new ResponseEntity<>(mostLikedPins, HttpStatus.OK);
    }

    @GetMapping("/pins-created-since")
    public ResponseEntity<Long> getPinsCreatedSince(@RequestParam("date") String dateString) {
        LocalDateTime startDate = LocalDateTime.parse(dateString);
        long pinsCreatedSince = statsService.getPinsCreatedSince(startDate);
        return new ResponseEntity<>(pinsCreatedSince, HttpStatus.OK);
    }
}
