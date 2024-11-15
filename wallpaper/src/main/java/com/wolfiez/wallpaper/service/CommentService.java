package com.wolfiez.wallpaper.service;


import com.wolfiez.wallpaper.DTO.CommentDto;
import com.wolfiez.wallpaper.entity.Comment;
import com.wolfiez.wallpaper.entity.Pin;
import com.wolfiez.wallpaper.entity.User;
import com.wolfiez.wallpaper.exception.PinNotFoundException;
import com.wolfiez.wallpaper.exception.UserNotFoundException;
import com.wolfiez.wallpaper.repository.CommentRepository;
import com.wolfiez.wallpaper.repository.PinRepository;
import com.wolfiez.wallpaper.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PinRepository pinRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PinRepository pinRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.pinRepository = pinRepository;
    }

    public Comment createComment(CommentDto commentDto, Long userId, Long pinId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new PinNotFoundException("Pin not found with id: " + pinId));

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setUser(user);
        comment.setPin(pin);

        return commentRepository.save(comment);
    }

    public Optional<Comment> getPinComments(Long pinId, int limit) {
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new PinNotFoundException("Pin not found with id: " + pinId));
        return commentRepository.findPinComments(pin, (PageRequest.of(0, limit)));
    }

    public List<Comment> getUserComments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return commentRepository.findByUser(user);
    }

    public long countPinComments(Long pinId) {
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new PinNotFoundException("Pin not found with id: " + pinId));
        return commentRepository.countPinComments(pin);
    }

    public List<Comment> searchComments(String keyword) {
        return commentRepository.searchComments(keyword);
    }
}