package dev.madiyar.vento.security;


import dev.madiyar.vento.dto.JwtAuthDto;
import dev.madiyar.vento.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${token.signing.key}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private long expiration;


    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }


    // Создаём токен — вызывается когда юзер логинится

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email) // кладем email внутрь токена
                .setIssuedAt(new Date()) // когда создан
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // когда истекает
                .signWith(getSigningKey()) // подписываем секретным ключом
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // возвращает email который мы положили в setSubject
    }

    // Проверяем что токен валидный и не истёк
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // если токен плохой — выбросит исключение
            return true;
        } catch (Exception e) {
            return false; // токен просрочен, подделан или неверный
        }
    }


}
