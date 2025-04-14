package entities;

import Enums.entitiesEnums.PERMISSION_NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PERMISSION_NAME permissionName;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private boolean isDeleted;

    private void load() {
        createdAt = LocalDate.now();
        isDeleted = false;
    }
}
