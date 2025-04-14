package entities;

import Enums.entitiesEnums.TOKEN_TYPE;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(unique = true, columnDefinition = "TEXT")
    private String token;

    @Enumerated(EnumType.STRING)
    private TOKEN_TYPE tokenType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean isExpired;

    @Column(nullable = false)
    private boolean isRevoked;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private LocalDate createdAt;

    @PrePersist
    private void load() {
        createdAt = LocalDate.now();
        isDeleted = false;
    }

}
