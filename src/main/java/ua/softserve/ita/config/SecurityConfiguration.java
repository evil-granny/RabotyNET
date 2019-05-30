package ua.softserve.ita.config;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] CSRF_IGNORE = {"/login/**", "/users/**","/resetPassword","/changePassword"};

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(ImmutableList.of("http://localhost:4200"));
            configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(ImmutableList.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization", "Content-Type", "Accept", "application/pdf", "X-XSRF-TOKEN"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()

                .cors()
                .and()

                .authorizeRequests()
                        .antMatchers("/companies/all/**","/companies/sendMail").access("hasRole('ROLE_ADMIN')")
                        .antMatchers("/companies/my","/RabotyNET/companies/update","/RabotyNET/companies/delete/**").access("hasRole('ROLE_COWNER')")
                        .antMatchers("/users").access("hasRole('ROLE_USER')")
                        .antMatchers("/resume/findByVacancyId/**").access("hasRole('ROLE_COWNER')")
                        .antMatchers("/resume/**","/RabotyNET/companies/create","/companies/approve","/people", "/RabotyNET/people/*", "/RabotyNET/people/**").access("hasRole('ROLE_USER') or hasRole('ROLE_COWNER')")
                        .antMatchers("/companies/byName/**","/companies/byCompany/**","/claims","/photo/**","/users/**", "/users/enabled/**").permitAll()
                        .antMatchers("/","/vacancies/**","/login","/login/**", "/resetPassword","/changePassword").permitAll()
                        .antMatchers("/pdf/**", "/updatePDF", "/createPdf/**","/healthCheck").permitAll()
                .anyRequest().authenticated()
                .and()

                .logout()
                        .logoutSuccessUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID","XSRF-TOKEN")
                .and()

                .csrf()
                        .ignoringAntMatchers(CSRF_IGNORE)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    public void configure(WebSecurity web) {
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
}
