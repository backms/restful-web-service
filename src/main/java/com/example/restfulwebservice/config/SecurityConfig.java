package com.example.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("minsuback")
                    .password("{noop}test1234")         // {noop} - 어느 동작없이 인코딩없이 사용할수 있는 설정 // 실사용시에는 패스워드 알고리즘을 사용
                    .roles("USER");
        }

}
