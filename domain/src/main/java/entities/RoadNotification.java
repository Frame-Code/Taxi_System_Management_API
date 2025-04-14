package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@Entity
public class RoadNotification extends Notification{

    @ManyToOne
    @JoinColumn(name = "idDriver")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;
}
