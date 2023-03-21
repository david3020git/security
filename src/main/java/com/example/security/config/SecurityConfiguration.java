package com.example.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import springfox.documentation.builders.PathSelectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider autheticationProvider;

    /**
     * Esta es la clase encargada de la seguridad  lo que hemos creado se usara desde esta clase
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http

                .csrf()
                .disable()
                .authorizeHttpRequests()
                //estoy autorizando todos los metodos de esq uest path
              // .requestMatchers( "/javainuse-openapi/**").permitAll()



                .requestMatchers("/api/v1/demo-users/**",
                        "/api/v1/auth/**",
                        "/api/v1/demo-books/**"
                       )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(autheticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);




        return http.build();
    }
}
