package ua.com.config;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String XSRF_TOKEN = "XSRF-TOKEN";
    private static final List<String> ALLOWED_METHODS = ImmutableList.of("GET", "POST", "PUT", "DELETE");
    private static final List<String> ALLOWED_HEADERS = ImmutableList.of("Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Authorization",
            "Content-Type",
            "Accept",
            "application/pdf",
            "X-XSRF-TOKEN");
    private static final String[] AUTH_WHITELIST = {
            "/swagger**/**",
            "/configuration/ui",
            "/swagger-ui.html#/",
            "/v2/api-docs",
            "/webjars/**",
            "/configuration/security",
            "/csrf"
    };
    private static final String[] CSRF_IGNORE = {"/login/**", "/users/**", "/password/**", "/searchVacancy"};
    private static final String[] ADMIN_URLS = {"/companies/all/**", "/companies/sendMail", "/people/*"};
    private static final String[] COWNER_URLS = {"/companies/my", "/companies/delete/**", "/searchResume",
            "/resume/findByVacancyId/**", "/showResume/**"};
    private static final String[] USER_URLS = {"/users", "/companies/create", "/companies/update"};
    private static final String[] COWNER_ADMIN_URLS = {"/companies/update"};
    private static final String[] COWNER_USER_URLS = {"/resume/**", "/companies/create", "/companies/approve", "/people", "/people/*", "people/**", "/companies/my", "/companies/update"};
    private static final String[] ALL_USERS_URLS = {"/companies/byName/**", "/companies/byCompany/**", "/claims", "/photo/**", "/users/**", "/users/enabled/**", "/users/getRoles/*",
            "/", "/vacancies/**", "/login", "/login/**", "/password/**", "/healthCheck", "/pdf/**", "/updatePDF", "/createPdf/**",
            "/sendResume/{vacancyId}", "/companies/byVacancyId/**", "/searchVacancy"};

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("https://rabotynetweb.herokuapp.com"));
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
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
                .antMatchers(ADMIN_URLS).access("hasRole('ROLE_ADMIN')")
                .antMatchers(COWNER_URLS).access("hasRole('ROLE_COWNER')")
                .antMatchers(USER_URLS).access("hasRole('ROLE_USER')")
                .antMatchers(COWNER_ADMIN_URLS).access("hasRole('ROLE_ADMIN') or hasRole('ROLE_COWNER')")
                .antMatchers(COWNER_USER_URLS).access("hasRole('ROLE_USER') or hasRole('ROLE_COWNER')")
                .antMatchers(ALL_USERS_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", XSRF_TOKEN)
                .and()

                .csrf()
                .ignoringAntMatchers(CSRF_IGNORE)
                .csrfTokenRepository(csrfTokenRepository())
                .and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                        .getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, XSRF_TOKEN);
                    String token = csrf.getToken();
                    if (cookie == null || token != null
                            && !token.equals(cookie.getValue())) {
                        cookie = new Cookie(XSRF_TOKEN, token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

}
