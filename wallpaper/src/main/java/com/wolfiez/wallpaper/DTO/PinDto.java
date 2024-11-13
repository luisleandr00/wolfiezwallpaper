package com.wolfiez.wallpaper.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
@Data
public class PinDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Long userId;
    private Set<Long> boardIds;
    private Set<CommentDto> comments;
    private Set<Long> likedByUserIds;
}
