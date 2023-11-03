package com.EmailSender.EmailSender.Security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    //256 en hexa
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    //crear token
    public String generateAccesToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //validar token
    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //obtener el username
    public String getUsernameFromToken(String token){
        return getClaim(token,Claims::getSubject);
    }

    //obtener solo 1 claim
    public <T> T getClaim(String token, Function<Claims, T>claimsTFunction){

        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    //obtener los todos datos
    public Claims extractAllClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    //obtener firma token
    public Key getSignatureKey(){
        byte[]keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
