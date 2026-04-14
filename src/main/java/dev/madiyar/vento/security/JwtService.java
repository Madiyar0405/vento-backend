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

    // none conventional name better secret
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
        // duplicate code with isTokenValid
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
            // documentation states that parser guarantee immutable and thread safe worth
            // to move initialization of parser to constructure
            Jwts.parser()
                    // deprecate API, better straight to use a new API
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // если токен плохой — выбросит исключение
            return true;
        } catch (Exception e) {
            return false; // токен просрочен, подделан или неверный
        }
    }


}
