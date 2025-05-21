package com.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/usermanagement/register", "/usermanagement/login", "/usermanagement/guest/AllGuests").permitAll()
                        .requestMatchers("/usermanagement/staff/**").hasAnyAuthority("STAFF", "MANAGER")
                        .anyRequest().authenticated())
              //  .httpBasic(httpBasic -> httpBasic.disable())
              //  .formLogin(formLogin -> formLogin.disable())
                .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        

		/*return http.csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(request -> request
						.requestMatchers("register","login","/guest/AllGuests").permitAll()
						//.requestMatchers("/guest/**").hasAnyAuthority("GUEST","MANAGER","STAFF")
						.requestMatchers("/staff/**").hasAnyAuthority("STAFF","MANAGER")
						.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> 
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build(); */
    }
}