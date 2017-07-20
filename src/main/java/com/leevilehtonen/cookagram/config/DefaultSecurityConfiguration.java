package com.leevilehtonen.cookagram.config;


import com.leevilehtonen.cookagram.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Profile({"default", "development"})
@Configuration
@EnableWebSecurity
public class DefaultSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers(HttpMethod.POST, "/accounts").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/index")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
                .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.inMemoryAuthentication().withUser("tester").password("123456").roles("USER");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
