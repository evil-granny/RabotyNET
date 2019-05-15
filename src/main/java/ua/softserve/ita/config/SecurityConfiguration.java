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
                .antMatchers("/admin", "/person/**").access("hasRole('ROLE_ADMIN')")
//                .antMatchers("/users").access("hasRole('ROLE_USER')")
                .antMatchers("/createCV").access("hasRole('ROLE_USER') or hasRole('ROLE_COWNER')")
                .antMatchers("/companies").access("hasRole('ROLE_COWNER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/searchCV").access("hasRole('ROLE_COWNER')")
                .antMatchers("/", "/vacancies", "/login", "/registrationConfirm/**", "/registration", "/users/**").permitAll()
                .antMatchers("/", "/vacancies/**", "/loginUser", "/registration").permitAll()
                .antMatchers("/", "/pdf/**", "/updatePDF", "/createPdf/**").permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/logout")
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
