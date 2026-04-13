package dev.madiyar.vento.entity;


import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name= "password_reset_tokens")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(name = "expiry_date")
    private OffsetDateTime expiryDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordResetToken() {}

    public PasswordResetToken(UUID id, String token, OffsetDateTime expiryDate, User user) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OffsetDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(OffsetDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
