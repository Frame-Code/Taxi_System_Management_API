package entities;

import Enums.NOTIFICATION_TYPE;

import java.time.LocalDate;

public class Notification {
    private Long id;
    private String title;
    private String message;
    private NOTIFICATION_TYPE notificationType;
    private LocalDate createdAt;
    private Driver driver;
    private Client client;
}
