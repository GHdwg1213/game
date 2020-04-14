package com.dwg.config;

import com.dwg.model.support.BaseResponse;
import com.dwg.service.auth.AuthUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 开启登录配置
        http.authorizeRequests()
                // 标识访问 `/xxx` 这个接口，需要具备`admin`角色
                .antMatchers("/xxx").hasRole("admin")
                // 允许匿名的url - 可理解为放行接口 - 多个接口使用,分割
                .antMatchers("/user/addUser").permitAll()
                // 其余所有请求都需要认证
                .anyRequest().authenticated()
                .and()
                // 设置登录认证页面
                .formLogin()
                .loginPage("/html/login.html")
                // 自定义登陆用户名和密码属性名，默认为 username和password
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/user/login")
                .successHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(BaseResponse.ok("登录成功!")));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, exception) -> {
                    resp.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(new BaseResponse<String>(false, "用户名或密码错误!", null)));
                    out.flush();
                    out.close();
                })
                .permitAll() // 和表单登录相关的接口统统都直接通过
                .and()
                .logout().logoutUrl("/logout")
                // 配置注销成功的回调
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(BaseResponse.ok("已经成功注销!")));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((req, resp, e) -> {
                    resp.setStatus(403);
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(new BaseResponse<String>(false, "权限不足，访问失败!", null)));
                    out.flush();
                    out.close();
                })
                .and()
                .rememberMe().rememberMeParameter("rememberMe");
    }

    @Resource
    private AuthUserService authUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
