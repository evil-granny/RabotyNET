package ua.softserve.ita.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return super.userDetailsService();
//    }

    @Bean
    public UserDetailsService userDetailsService(){
        // ensure the passwords are encoded properly
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("qwerty").roles("USER").build());
        manager.createUser(users.username("admin").password("admin").roles("USER","ADMIN").build());
        return manager;
    }

//    @Autowired
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .httpBasic();
        http
            .authorizeRequests()
                .antMatchers("/", "/signup", "/login").permitAll()
                .antMatchers("/", "/persons").hasAnyRole("USER")
                .anyRequest().hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout().permitAll().logoutSuccessUrl("/login")
                .and()
                .csrf().disable();
    }

    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }
}
