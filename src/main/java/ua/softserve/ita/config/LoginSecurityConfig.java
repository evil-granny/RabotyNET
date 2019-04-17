package ua.softserve.ita.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler successUrlHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").authorities("ROLE_USER")
                .and()
                .withUser("admin").password("{noop}admin").authorities("ROLE_USER","ROLE_ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/homePage").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/userPage").access("hasRole('ROLE_USER')")
                .antMatchers("/adminPage").access("hasRole('ROLE_ADMIN')")
                .and()
                .formLogin().loginPage("/loginPage")
                .failureUrl("/loginPage?error")
                .usernameParameter("username").passwordParameter("password")
                .successHandler(successUrlHandler)
                .and()
                .logout().logoutSuccessUrl("/loginPage?logout");
    }
}
