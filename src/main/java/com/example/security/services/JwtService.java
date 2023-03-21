package com.example.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "8DCC6467E87DC4FDB5278D86291BD8DCC6467E87DC4FDB5278D86291BD";

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject );
    }

    /**
     * Jwst ->viene de jsont.token
     * Claims son los datos del token
     * @param token
     * @return
     *
     *
     */
    private Claims extractAllClaims(String token){
        return Jwts
                //pasmos el token a string
                .parserBuilder()
                // lo firmamos para que sea valido
                .setSigningKey(getSignInKey())
                // lo construios
                .build()
                //llamos a esta clase con nuestro token que cabamos de consttruir
                .parseClaimsJws(token)
                .getBody();
    }



    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims  claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Creacion del token a partir solo de los datos del usuario
     */

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }


    /**
     * Creación del token
     * @param extraClaims,
     * @param userDetails
     * @return
     */

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                //extramos el username atraves de los claims
                .setSubject(userDetails.getUsername())
                //asignamos fecha de creacion
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //agregamos fecha de caducidad del token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                //firmamos el token
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                //guardamos los cambios realizados
                .compact();
    }




    /**
     * Firmar el token
     * @return
     */

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Clase para validar token
     * @return verifica si el nombre de usuario es el mismo al nombre d eusuario del token
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Validar si el token no ha caducado
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);

    }
}
