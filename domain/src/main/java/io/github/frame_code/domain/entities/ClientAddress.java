package io.github.frame_code.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ClientAddress extends Address{
    @OneToOne
    @JoinColumn(name = "idClient")
    private Client client;
}
