package dev.madiyar.vento.dto;

// it is mo like internal structure the `security` package more appropriate
// setter never used better to use record and get immutablity and safety out of the box
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
