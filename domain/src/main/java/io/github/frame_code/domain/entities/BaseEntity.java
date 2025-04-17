package io.github.frame_code.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Clase base para campos de auditoría comunes en todas las entidades
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity {
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false, length = 100)
    private String createdBy;
    
    private LocalDateTime updatedAt;
    
    @Column(length = 100)
    private String updatedBy;
    
    @Column(nullable = false)
    private boolean isDeleted;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        isDeleted = false;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 