package io.github.frame_code.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String names;

    @Column(length = 60, nullable = false)
    private String lastNames;

    @Column(length = 80, nullable = false, unique = true)
    private String email;

    @Column(length = 15, nullable = false, unique = true)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String passwordHash;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private boolean isDeleted;

    private LocalDate updatedAt;
    private String updatedBy;
    private String photo;

    @Column(nullable = false)
    private LocalDate bornDate;

    public String getFullNames() {return names + " " + lastNames;}

    @PrePersist
    private void load() {
        createdAt = LocalDate.now();
        isDeleted = false;
    }
    @PreUpdate
    private void update() {
        updatedAt = LocalDate.now();
    }
}
