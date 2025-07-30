package com.example.cinemaapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.cinemaapi.service.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/unidades/**")
                .permitAll()
                // .authenticated()
                .antMatchers("/api/v1/tipoassentos/**")
                .permitAll()
                .antMatchers("/api/v1/tipoexibicoes/**")
                .permitAll()
                .antMatchers("/api/v1/compras/**")
                .permitAll().antMatchers("/api/v1/salas/**")
                .permitAll()
                .antMatchers("/api/v1/assentos/**")
                .permitAll()
                .antMatchers("/api/v1/filmes/**")
                .permitAll()
                .antMatchers("/api/v1/sessoes/**")
                .permitAll()
                .antMatchers("/api/v1/generos/**")
                .permitAll()
                .antMatchers("/api/v1/ingressos/**")
                .permitAll()
                .antMatchers("/api/v1/precos/**")
                .permitAll()
                .antMatchers("/api/v1/classificacaoindicativas/**")
                .permitAll()
                .antMatchers("/api/v1/ingressos/**")
                .permitAll()
                .antMatchers("/api/v1/clientes/**")
                .permitAll()
                .antMatchers("/api/v1/cinemaadmins/**")
                .permitAll()
                .antMatchers("/api/v1/usuarios/**")
                .hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
        // .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}