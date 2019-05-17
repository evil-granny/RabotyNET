package ua.softserve.ita.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/swagger**/**",
            "/configuration/ui",
            "/swagger-ui.html#/",
            "/v2/api-docs",
            "/webjars/**",
            "/configuration/security",
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/users").access("hasRole('ROLE_USER')")
                .antMatchers("/createCV").access("hasRole('ROLE_USER') or hasRole('ROLE_COWNER')")
                .antMatchers("/companies/**").access("hasRole('ROLE_COWNER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/searchCV").access("hasRole('ROLE_COWNER')")
                .antMatchers("/", "/vacancies", "/login", "/registrationConfirm/**", "/registration", "/users/**").permitAll()
                .antMatchers("/", "/vacancies/**", "/loginUser", "/registration").permitAll()
                .antMatchers("/people", "/people/*", "people/**").access("hasRole('ROLE_USER') or hasRole('ROLE_COWNER')")
                .antMatchers("/", "/pdf/**", "/updatePDF", "/createPdf/**").permitAll()
                .anyRequest().permitAll()
//                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/logout")
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

}
