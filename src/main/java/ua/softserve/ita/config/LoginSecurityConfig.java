package ua.softserve.ita.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler successUrlHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/adminPage", "/persons","/personInfo").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/cownerPage").access("hasRole('ROLE_COWNER')")
                .antMatchers("/userPage").access("hasRole('ROLE_USER')")
                .antMatchers("/person/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_COWNER')")
                .antMatchers("/homePage", "/persons").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_COWNER')")
                .antMatchers("/", "/user/registration").permitAll()
                .and()
                .formLogin().loginPage("/loginPage")
                .failureUrl("/loginPage?error")
                .usernameParameter("username").passwordParameter("password")
                .successHandler(successUrlHandler)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .logout().logoutSuccessUrl("/loginPage?logout")
                .and().csrf().disable();
    }
}
