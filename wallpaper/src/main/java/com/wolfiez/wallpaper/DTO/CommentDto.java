package com.wolfiez.wallpaper.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommentDto {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private Long userId;
        private Long pinId;
}
