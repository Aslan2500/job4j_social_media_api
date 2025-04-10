package ru.job4j.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id",
            nullable = false)
    private User user;

    @Column(name = "title",
            nullable = false)
    private String title;

    @Column(name = "content",
            nullable = false)
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "crated_at",
            nullable = false)
    private OffsetDateTime createdAt;
}
