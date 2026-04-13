package dev.madiyar.vento.dto;

public class JwtAuthDto {
    private String token;

    public JwtAuthDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
