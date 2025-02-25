package com.logintec.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Informa que é uma classe que cuida da configuracao da Web
public class SecurityConfig {
	
	/*
	 *Objetivo:
	 *		Essa classe configura as regras de segurança da aplicação.
			Define a política de sessão como STATELESS, ou seja, sem sessões (apenas JWT).
			Configura endpoints públicos (/auth/login e /auth/register).
			Adiciona o filtro SecurityFilter antes do UsernamePasswordAuthenticationFilter.
	 * */

	
	@Autowired
	SecurityFilter securityFilter;
	
	
	/*
	 * 	Desativa CSRF (.csrf(csrf -> csrf.disable())).
			Configura JWT como autenticação (session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
			Define quais endpoints são públicos:
				POST /auth/login
				POST /auth/register
				Todos os outros exigem autenticação (.anyRequest().authenticated()).
			Adiciona o SecurityFilter antes do UsernamePasswordAuthenticationFilter, garantindo que a autenticação ocorra antes que o Spring Security processe a requisição.
		
	 * 
	 * */
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

	
	/*
	 * Objetivo: Define que o encoder de senhas será o BCrypt (BCryptPasswordEncoder()).
			Importante: As senhas dos usuários serão criptografadas antes de serem armazenadas no banco.
	 */
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
	/*
	 * Objetivo: Obtém o gerenciador de autenticação a partir da configuração do Spring Security.
	 */
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    
}
