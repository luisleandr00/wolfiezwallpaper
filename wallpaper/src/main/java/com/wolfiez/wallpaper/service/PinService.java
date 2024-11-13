package com.wolfiez.wallpaper.service;

import com.wolfiez.wallpaper.DTO.PinDto;
import com.wolfiez.wallpaper.entity.Pin;
import com.wolfiez.wallpaper.entity.User;
import com.wolfiez.wallpaper.exception.PinNotFoundException;
import com.wolfiez.wallpaper.exception.UserNotFoundException;
import com.wolfiez.wallpaper.repository.PinRepository;
import com.wolfiez.wallpaper.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class PinService {
    private final PinRepository pinRepository;
    private final UserRepository userRepository;

    public PinService(PinRepository pinRepository, UserRepository userRepository) {
        this.pinRepository = pinRepository;
        this.userRepository = userRepository;
    }

    public Pin createPin(PinDto pinDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Pin pin = new Pin();
        pin.setTitle(pinDto.getTitle());
        pin.setDescription(pinDto.getDescription());
        pin.setImageUrl(pinDto.getImageUrl());
        pin.setUser(user);

        return pinRepository.save(pin);
    }

    public Pin getPin(Long id) {
        return pinRepository.findById(id)
                .orElseThrow(() -> new PinNotFoundException("Pin not found with id: " + id));
    }

    public List<Pin> getPinsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return pinRepository.findByUser(user);
    }

    public List<Pin> searchPins(String keyword) {
        return pinRepository.searchPins(keyword);
    }

    public List<Pin> getMostLikedPins(int limit) {
        return pinRepository.findMostLikedPins(PageRequest.of(0, limit));
    }

    public List<Pin> getRecentPins(int limit) {
        return pinRepository.findRecentPins(PageRequest.of(0, limit));
    }

    public boolean toggleLike(Long pinId, Long userId) {
        Pin pin = getPin(pinId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (pinRepository.isLikedByUser(pinId, userId)) {
            pin.getLikes().remove(user);
            return false;
        } else {
            pin.getLikes().add(user);
            return true;
        }
    }
}
