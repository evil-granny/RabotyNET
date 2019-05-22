package ua.softserve.ita.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import ua.softserve.ita.filter.CustomCsrfFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] CSRF_IGNORE = {"/login/**", "/registration/**"};

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
                        .antMatchers("/companies/byName/**","/companies/byCompany/**","/claims","/photo/**","/users/**").permitAll()
                        .antMatchers("/","/vacancies/**","/login","/login/**","/registration","/registrationConfirm/**", "/resetPassword","/changePassword").permitAll()
                        .antMatchers("/pdf/**", "/updatePDF", "/createPdf/**").permitAll()
                        .anyRequest().authenticated()
                        .antMatchers("/companies/all/**","/companies/sendMail").access("hasRole('ROLE_ADMIN')")
                        .antMatchers("/companies/my","/companies/update","/companies/delete/**","/searchCV").access("hasRole('ROLE_COWNER')")
                        .antMatchers("/users").access("hasRole('ROLE_USER')")
                        .antMatchers("/createCV","/companies/create","/companies/approve","/people", "/people/*", "people/**").access("hasRole('ROLE_USER') or hasRole('ROLE_COWNER')")
                .and()

                .logout()
                        .logoutSuccessUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                .and()

                .csrf()
                .ignoringAntMatchers(CSRF_IGNORE) // URI where CSRF check will not be applied
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // defines a repository where tokens are stored
                .and()
                .addFilterBefore(new CustomCsrfFilter(), CsrfFilter.class);
//                .disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger**/**",
            "/configuration/ui",
            "/swagger-ui.html#/",
            "/v2/api-docs",
            "/webjars/**",
            "/configuration/security",
            "/csrf"
    };

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(CustomCsrfFilter.CSRF_COOKIE_NAME);
        return repository;
    }
}
