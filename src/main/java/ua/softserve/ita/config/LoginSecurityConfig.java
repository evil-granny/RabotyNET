package ua.softserve.ita.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    private static Logger log = Logger.getLogger(LoginSecurityConfig.class.getName());

    @Autowired
    private AuthenticationSuccessHandler successUrlHandler;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}user").authorities("ROLE_USER")
//                .and()
//                .withUser("cowner").password("{noop}cow").authorities("ROLE_COWNER","ROLE_USER")
//                .and()
//                .withUser("admin").password("{noop}admin").authorities("ROLE_COWNER","ROLE_USER","ROLE_ADMIN");
//    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    };

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/homePage").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_COWNER')")
                .antMatchers("/cownerPage", "/persons").access("hasRole('ROLE_COWNER')")
                .antMatchers("/userPage").access("hasRole('ROLE_USER')")
                .antMatchers("/adminPage", "/persons", "/person/**","/personInfo").access("hasRole('ROLE_ADMIN')")
                .and()
                .formLogin().loginPage("/loginPage")
                .failureUrl("/loginPage?error")
                .usernameParameter("username").passwordParameter("password")
                .successHandler(successUrlHandler)
                .and()
                .logout().logoutSuccessUrl("/loginPage?logout");
    }
}
