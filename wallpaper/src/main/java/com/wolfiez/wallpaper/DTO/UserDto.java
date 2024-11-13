package com.wolfiez.wallpaper.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private String profileImage;
    private LocalDateTime createdAt;
    private Set<String> roles;
    private Set<PinDto> pins;
    private Set<BoardDto> boards;

}
