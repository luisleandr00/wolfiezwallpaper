package com.wolfiez.wallpaper.controller;


import com.wolfiez.wallpaper.DTO.BoardDto;
import com.wolfiez.wallpaper.entity.Board;
import com.wolfiez.wallpaper.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody BoardDto boardDto, @RequestHeader("userId") Long userId) {
        Board createdBoard = boardService.createBoard(boardDto, userId);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable Long id) {
        Board board = boardService.getBoard(id);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Board>> getUserBoards(@PathVariable Long userId) {
        List<Board> userBoards = boardService.getUserBoards(userId);
        return new ResponseEntity<>(userBoards, HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Board>> getPublicBoards() {
        List<Board> publicBoards = boardService.getPublicBoards();
        return new ResponseEntity<>(publicBoards, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Board>> searchUserBoards(@RequestHeader("userId") Long userId, @RequestParam("keyword") String keyword) {
        List<Board> searchResults = boardService.searchUserBoards(userId, keyword);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @PostMapping("/{boardId}/pins/{pinId}")
    public ResponseEntity<Void> addPinToBoard(@PathVariable Long boardId, @PathVariable Long pinId) {
        boardService.addPinToBoard(boardId, pinId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/pins/{pinId}")
    public ResponseEntity<Void> removePinFromBoard(@PathVariable Long boardId, @PathVariable Long pinId) {
        boardService.removePinFromBoard(boardId, pinId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{boardId}/pins/count")
    public ResponseEntity<Long> countPinsInBoard(@PathVariable Long boardId) {
        long pinsCount = boardService.countPinsInBoard(boardId);
        return new ResponseEntity<>(pinsCount, HttpStatus.OK);
    }
}
