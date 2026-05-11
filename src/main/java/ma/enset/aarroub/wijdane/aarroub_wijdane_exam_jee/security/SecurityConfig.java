package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(ar -> ar
                        // Public endpoints
                        .requestMatchers("/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Protected API logic based on roles
                        .requestMatchers("/api/agences/**").hasAnyAuthority("ROLE_CLIENT", "ROLE_EMPLOYE", "ROLE_ADMIN")
                        .requestMatchers("/api/vehicles/**").hasAnyAuthority("ROLE_EMPLOYE", "ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .headers(h -> h.frameOptions(fo -> fo.disable()))
                .build();
    }
}