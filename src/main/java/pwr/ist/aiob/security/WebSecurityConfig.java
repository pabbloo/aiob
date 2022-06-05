package pwr.ist.aiob.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pwr.ist.aiob.filters.JwtRequestFilter;

import javax.annotation.Resource;

@EnableWebSecurity
public class WebSecurityConfig {

    @Configuration
    @Order(1)
    public static class InsecureWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/insecure")
                    .authorizeRequests().antMatchers("/insecure").authenticated()
                    .and().httpBasic();

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().and().csrf().disable();
        }
    }

    @Configuration
    public static class MainWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Resource
        private UserDetailsService userDetailsService;

        @Resource
        private JwtRequestFilter jwtRequestFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/login", "/swagger-ui/**","/swagger-ui**", "/v3/api-docs/", "/v3/api-docs/**", "/signup")
                    .permitAll();

            http.authorizeRequests()
                    .antMatchers("/**").authenticated()
                    .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().and().csrf().disable();
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(getPasswordEncoder());
            return provider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(authenticationProvider());
        }

        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}
