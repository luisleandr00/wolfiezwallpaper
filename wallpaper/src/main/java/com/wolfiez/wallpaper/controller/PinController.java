package com.wolfiez.wallpaper.controller;

import com.wolfiez.wallpaper.DTO.PinDto;
import com.wolfiez.wallpaper.entity.Pin;
import com.wolfiez.wallpaper.service.PinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pins")
public class PinController {
    private final PinService pinService;

    public PinController(PinService pinService) {
        this.pinService = pinService;
    }

    @PostMapping
    public ResponseEntity<Pin> createPin(@RequestBody PinDto pinDto, @RequestHeader("userId") Long userId) {
        Pin createdPin = pinService.createPin(pinDto, userId);
        return new ResponseEntity<>(createdPin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pin> getPin(@PathVariable Long id) {
        Pin pin = pinService.getPin(id);
        return new ResponseEntity<>(pin, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Pin>> getPinsByUser(@PathVariable Long userId) {
        List<Pin> userPins = pinService.getPinsByUser(userId);
        return new ResponseEntity<>(userPins, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Pin>> searchPins(@RequestParam("keyword") String keyword) {
        List<Pin> searchResults = pinService.searchPins(keyword);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/most-liked")
    public ResponseEntity<List<Pin>> getMostLikedPins(@RequestParam(defaultValue = "10") int limit) {
        List<Pin> mostLikedPins = pinService.getMostLikedPins(limit);
        return new ResponseEntity<>(mostLikedPins, HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Pin>> getRecentPins(@RequestParam(defaultValue = "10") int limit) {
        List<Pin> recentPins = pinService.getRecentPins(limit);
        return new ResponseEntity<>(recentPins, HttpStatus.OK);
    }

    @PostMapping("/{pinId}/like")
    public ResponseEntity<Boolean> toggleLike(@PathVariable Long pinId, @RequestHeader("userId") Long userId) {
        boolean isLiked = pinService.toggleLike(pinId, userId);
        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }
}