package br.ifms.edu.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            .csrf(csrf -> csrf.disable())

            .cors(Customizer.withDefaults())

            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth
                
                // Permite requisições GET para /api/livros, /autores, /editoras
                .requestMatchers(HttpMethod.GET, "/api/livros/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/autores/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/editoras/**").permitAll()

                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

                .anyRequest().authenticated() 
            )

            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}