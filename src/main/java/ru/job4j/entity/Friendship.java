package ru.job4j.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.entity.enums.Status;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "friendship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "requester_id",
            nullable = false)
    private User requester;

    @OneToOne
    @JoinColumn(name = "addressee_id",
            nullable = false)
    private User addressee;

    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
