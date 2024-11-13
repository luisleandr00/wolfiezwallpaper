package com.wolfiez.wallpaper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pinLikeStats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PinLikeStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pinId;
    @Column(nullable = false)
    private String pinTitle;


    private long likeCount;

}
