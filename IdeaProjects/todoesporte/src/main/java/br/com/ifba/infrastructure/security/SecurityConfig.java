package br.com.ifba.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Habilita o CORS usando a configuração definida abaixo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Desabilita a proteção CSRF (necessário para APIs REST com POST/PUT/DELETE)
                .csrf(AbstractHttpConfigurer::disable)

                // 3. Define a gestão de sessão como Stateless (sem criar sessão HTTP no servidor)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Configuração das regras de acesso às rotas
                .authorizeHttpRequests(auth -> auth
                        // Permite todas as requisições do tipo OPTIONS (Preflight do navegador)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Permite acesso público a todas as rotas da API durante o desenvolvimento
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    /**
     * Define as regras do CORS para permitir requisições vindas do Frontend na Vercel.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origens permitidas: Domínio do seu frontend na Vercel e localhost para testes
        configuration.setAllowedOriginPatterns(List.of("*"));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Cabeçalhos permitidos nas requisições
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Accept"));

        // Permite o envio de credenciais (se necessário)
        configuration.setAllowCredentials(true);

        // Aplica a configuração a todas as rotas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}