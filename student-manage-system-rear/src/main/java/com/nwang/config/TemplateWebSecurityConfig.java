package com.nwang.config;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nwang.common.ResponseEnum;
import com.nwang.common.ServerResponse;
import com.nwang.entity.User;
import com.nwang.util.PrintUtil;
import com.nwang.util.TokenUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.nwang.repository.UserRepository;
import com.nwang.service.impl.TemplateUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TemplateWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TemplateUserDetailsService templateUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/user/create",
                        "/swagger*//**",
                        "/v2/api-docs",
                        "/webjars*//**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and().logout()
                .logoutSuccessUrl("/user/login")
//                .logoutSuccessHandler(onLogoutSuccess)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(templateUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @SneakyThrows
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                User user = userRepository.findByUserName(authentication.getName());
                String token = TokenUtil.createToken(Long.toString(user.getId()), user.getUserName());
                user.setToken(token);
                userRepository.save(user);
                HashMap dataMap = new HashMap();
                dataMap.put("token", token);
                System.out.println("=======>登录成功");
                PrintUtil.response(response,
                        ServerResponse.getInstance().responseEnum(ResponseEnum.LOGIN_SUCCESS).data(dataMap).toString());
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws IOException, ServletException {
                if (exception instanceof UsernameNotFoundException) {
                    PrintUtil.response(response,
                            ServerResponse.getInstance().responseEnum(ResponseEnum.ACCOUNT_NOT_FOUND).toString());
                    return;
                }
                PrintUtil.response(response,
                        ServerResponse.getInstance().responseEnum(ResponseEnum.LOGIN_FAILED).toString());
            }
        };
    }

}