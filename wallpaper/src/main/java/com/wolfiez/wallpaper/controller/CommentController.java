package com.wolfiez.wallpaper.controller;

import com.wolfiez.wallpaper.DTO.CommentDto;
import com.wolfiez.wallpaper.entity.Comment;
import com.wolfiez.wallpaper.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto, @RequestHeader("userId") Long userId, @RequestHeader("pinId") Long pinId) {
        Comment createdComment = commentService.createComment(commentDto, userId, pinId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/pin/{pinId}")
    public ResponseEntity<Optional<Comment>> getPinComments(@PathVariable Long pinId, @RequestParam(defaultValue = "20") int limit) {
        Optional<Comment> pinComments = commentService.getPinComments(pinId, limit);
        return new ResponseEntity<>(pinComments, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable Long userId) {
        List<Comment> userComments = commentService.getUserComments(userId);
        return new ResponseEntity<>(userComments, HttpStatus.OK);
    }

    @GetMapping("/pin/{pinId}/count")
    public ResponseEntity<Long> countPinComments(@PathVariable Long pinId) {
        long commentCount = commentService.countPinComments(pinId);
        return new ResponseEntity<>(commentCount, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Comment>> searchComments(@RequestParam("keyword") String keyword) {
        List<Comment> searchResults = commentService.searchComments(keyword);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }
}