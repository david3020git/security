package com.example.security.config;

import com.example.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro para las solictudes http a mi aplicacion estos tres parametros me permitern controlar el flujo de
 * mi aplicación ya que des request puedo obtener datos y devolver un response
 * @NonNull -> ya que esto no puede ser nulo asi lo delclaramos
 *
 * @RequiredArgsConstructor me construlle el contrusctuor con cualquier atributo que le ponga ami clase ejemplo el nombre o user name
 * usaria esos atributos para crear el constructor de la clase asiq ue nos evita tenr que crear un contructor
 *
 * final String atuHeader = request.getHeader("Authorization"); -> este es el encabezadoq ue contiene el token esto envia en las peticones
 *  http en esta lnea tambien le damos el nombe al encabezado authorization
 *
 *
 *
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull  FilterChain filterChain) throws ServletException, IOException {
        final String autHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (autHeader == null || !autHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        //la posicion 7 es el espacion en Bearer
        jwt = autHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        // si el email es distinto de nul y usuario conectado no esta autenticado

        //SecurityContextHolder.getContext().getAuthentication() == null -> si esto es null el usuario no esta autenticado
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        // el null lo paso porque al crear un usuario aun no tiene credenciales por eso el null
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }
        filterChain.doFilter(request, response);
    }
}
