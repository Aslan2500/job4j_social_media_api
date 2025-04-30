package ru.job4j.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",
            nullable = false)
    private String username;

    @Column(name = "email",
            nullable = false)
    @Length(min = 4,
            max = 20,
            message = "email должно быть не менее 6 и не более 10 символов")
    private String email;

    @Column(name = "password",
            nullable = false)
    private String password;

    @Column(name = "created_at",
            nullable = false)
    private OffsetDateTime createdAt;
}
