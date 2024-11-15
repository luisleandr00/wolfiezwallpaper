package com.wolfiez.wallpaper.service;

import com.wolfiez.wallpaper.DTO.UserDto;
import com.wolfiez.wallpaper.entity.User;
import com.wolfiez.wallpaper.exception.DuplicateResourceException;
import com.wolfiez.wallpaper.exception.UserNotFoundException;
import com.wolfiez.wallpaper.repository.RoleRepository;
import com.wolfiez.wallpaper.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + registrationDto.getEmail());
        }

        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("ROLE_USER not found"))));

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User updateUser(Long id, UserDto updateDto) {
        User user = getUserById(id);

        if (!user.getEmail().equals(updateDto.getEmail()) && userRepository.existsByEmail(updateDto.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + updateDto.getEmail());
        }

        user.setName(updateDto.getName());
        user.setEmail(updateDto.getEmail());
        user.setProfileImage(updateDto.getProfileImage());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public List<User> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword);
    }

    public List<User> getUsersByRole(String roleName) {
        return userRepository.findByRoleName(roleName);
    }
}