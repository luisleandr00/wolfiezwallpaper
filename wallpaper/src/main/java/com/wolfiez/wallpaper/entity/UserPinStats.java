package com.wolfiez.wallpaper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userPinsStats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPinStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String username;

    private long pinCount;
}