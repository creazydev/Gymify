package com.github.Gymify.security.config;

import com.github.Gymify.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)

@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, RequestHeaderAuthenticationFilter.class)
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
}
