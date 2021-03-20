package pl.movie.registration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pl.movie.registration.service.impl.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler customSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    public SecurityConfig(UserDetailsService userDetailsService, AuthenticationSuccessHandler customSuccessHandler, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.customSuccessHandler = customSuccessHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(authProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register", "/home","/h2-console/**")
                .permitAll()
                .antMatchers("/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/home").hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .successHandler(customSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .invalidateHttpSession(true)
                .permitAll()
                .and()
//                        .logoutSuccessUrl("/home")
//                        .deleteCookies(cookieNamesToClear)
                .headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**");
    }
}
