package com.FinalExam.pharmacy.filters;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import static org.springframework.security.config.Customizer.withDefaults;
@Component
@EnableWebSecurity
@AllArgsConstructor
public class AuthenticationFilter {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register/**").permitAll()
                        .requestMatchers("/api/users/login/**").permitAll()
                        .requestMatchers("/api/users/logout/**").permitAll()
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers("/api/customer/orders/**").permitAll()
                        .requestMatchers("/api/customer/order-items/**").permitAll()
                        .requestMatchers("/api/customer/order-items/order/**").permitAll()
                        .requestMatchers("/api/admin/categories/**").permitAll()
                        .requestMatchers("/api/admin/products/**").permitAll()
                        .requestMatchers("/api/admin/orders/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                //.csrf(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf .ignoringRequestMatchers(
                        new AntPathRequestMatcher("/api/users/register/**"),
                        new AntPathRequestMatcher("/api/users/login/**"),
                        new AntPathRequestMatcher("/api/users/logout/**"),
                        new AntPathRequestMatcher("/api/users/**"),
                        new AntPathRequestMatcher("/api/customer/orders/**"),
                        new AntPathRequestMatcher("/api/customer/order-items/**"),
                        new AntPathRequestMatcher("/api/customer/order-items/order/**"),
                        new AntPathRequestMatcher("/api/admin/categories/**"),
                        new AntPathRequestMatcher("/api/admin/products/**"),
                        new AntPathRequestMatcher("/api/admin/orders/**")
                ));
        return http.build();
    }
}
