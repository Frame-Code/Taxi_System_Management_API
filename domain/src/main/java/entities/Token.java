package entities;

import Enums.TOKEN_TYPE;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter@Setter
public class Token {
    private Long id;
    private String token;
    private TOKEN_TYPE tokenType;
    private User user;
    private boolean isExpired;
    private boolean isRevoked;
    private boolean isDeleted;
    private LocalDate createdAt;

}
