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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/admin","/person/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/users").access("hasRole('ROLE_USER')")
                .antMatchers("/companies").access("hasRole('ROLE_COWNER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/searchCV").access("hasRole('ROLE_COWNER') or hasRole('ROLE_USER')")
                .antMatchers("/", "/vacancies", "/loginUser", "/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/logout")
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
