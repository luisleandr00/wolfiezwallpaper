package com.wolfiez.wallpaper.service;

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

    public Pin createPin(Pin pin, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        pin.setUser(user);
        return pinRepository.save(pin);
    }

    public List<Pin> getMostLikedPins(int limit) {
        return pinRepository.findMostLikedPins(PageRequest.of(0, limit));
    }

    public boolean toggleLike(Long pinId, Long userId) {
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new PinNotFoundException("Pin not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (pin.getLikes().contains(user)) {
            pin.getLikes().remove(user);
            return false;
        } else {
            pin.getLikes().add(user);
            return true;
        }
    }
}
