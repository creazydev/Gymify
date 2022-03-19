package com.github.Gymify.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/resources/**",
                        "/public/**",
                        "/static/**",
                        "/assets/**"
                );
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .authorizeRequests(configurer ->
                        configurer
                                .antMatchers(HttpMethod.GET, SecurityProperties.IGNORE_ANT_MATCHERS_GET)
                                .permitAll()
                                .antMatchers(HttpMethod.POST, SecurityProperties.IGNORE_ANT_MATCHERS_POST)
                                .permitAll()
                                .antMatchers(HttpMethod.PUT, SecurityProperties.IGNORE_ANT_MATCHERS_PUT)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                );
    }
}
