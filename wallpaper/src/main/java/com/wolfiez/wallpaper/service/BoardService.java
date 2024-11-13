package com.wolfiez.wallpaper.service;

import com.wolfiez.wallpaper.DTO.BoardDto;
import com.wolfiez.wallpaper.entity.Board;
import com.wolfiez.wallpaper.entity.Pin;
import com.wolfiez.wallpaper.entity.User;
import com.wolfiez.wallpaper.exception.BoardNotFoundException;
import com.wolfiez.wallpaper.exception.PinNotFoundException;
import com.wolfiez.wallpaper.exception.UserNotFoundException;
import com.wolfiez.wallpaper.repository.BoardRepository;
import com.wolfiez.wallpaper.repository.PinRepository;
import com.wolfiez.wallpaper.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PinRepository pinRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository, PinRepository pinRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.pinRepository = pinRepository;
    }

    public Board createBoard(BoardDto boardDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Board board = new Board();
        board.setName(boardDto.getName());
        board.setDescription(boardDto.getDescription());
        board.setPrivate(boardDto.isPrivate());
        board.setUser(user);

        return boardRepository.save(board);
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board not found with id: " + id));
    }

    public List<Board> getUserBoards(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return boardRepository.findByUser(user);
    }

    public List<Board> getPublicBoards() {
        return boardRepository.findPublicBoards();
    }

    public List<Board> searchUserBoards(Long userId, String keyword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return boardRepository.searchUserBoards(user, keyword);
    }

    public void addPinToBoard(Long boardId, Long pinId) {
        Board board = getBoard(boardId);
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new PinNotFoundException("Pin not found with id: " + pinId));
        board.getPins().add(pin);
        boardRepository.save(board);
    }

    public void removePinFromBoard(Long boardId, Long pinId) {
        Board board = getBoard(boardId);
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new PinNotFoundException("Pin not found with id: " + pinId));
        board.getPins().remove(pin);
        boardRepository.save(board);
    }

    public long countPinsInBoard(Long boardId) {
        return boardRepository.countPinsInBoard(boardId);
    }
}
