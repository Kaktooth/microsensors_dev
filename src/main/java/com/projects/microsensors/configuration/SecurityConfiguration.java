package com.projects.microsensors.configuration;

import com.projects.microsensors.common.AppConstraints.Authentication;
import com.projects.microsensors.common.AppConstraints.ExtendedPath;
import com.projects.microsensors.common.AppConstraints.Path;
import com.projects.microsensors.common.auth.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(DataSource dataSource,
                                 PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/", ExtendedPath.STATIC, Path.FAVICON, ExtendedPath.MAIN_PAGE, ExtendedPath.JS,
                        ExtendedPath.CSS, ExtendedPath.SIGN_UP_PAGE, ExtendedPath.API,
                        ExtendedPath.LOG_IN_PAGE)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage(Path.LOG_IN_PAGE)
                        .defaultSuccessUrl(Path.DASHBOARD_PAGE, true)
                        .failureUrl(Path.LOG_IN_PAGE + ExtendedPath.ERROR_ATTRIBUTE)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Autowired
    void configureGlobal(
            AuthenticationManagerBuilder auth,
            LoginAuthenticationProvider authenticationProvider
    ) throws Exception {

        auth.authenticationProvider(authenticationProvider)
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery(Authentication.GET_USER_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(Authentication.GET_AUTHORITY_BY_USERNAME_QUERY);
    }
}