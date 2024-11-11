package com.wolfiez.wallpaper.service;

import com.wolfiez.wallpaper.entity.User;
import com.wolfiez.wallpaper.exception.DuplicateResourceException;
import com.wolfiez.wallpaper.exception.UserNotFoundException;
import com.wolfiez.wallpaper.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        if (!user.getEmail().equals(userDetails.getEmail()) &&
                userRepository.existsByEmail(userDetails.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + userDetails.getEmail());
        }

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        // actualizar otros campos según sea necesario

        return userRepository.save(user);
    }
}