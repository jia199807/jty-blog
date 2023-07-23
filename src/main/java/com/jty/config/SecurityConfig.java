package com.jty.config;

import com.alibaba.fastjson2.JSON;
import com.jty.domain.entity.LoginUser;
import com.jty.domain.entity.User;
import com.jty.domain.entity.vo.BlogUserLoginVo;
import com.jty.domain.entity.vo.UserInfoVo;
import com.jty.filter.JsonLoginFilter;
import com.jty.filter.JwtAuthenticationTokenFilter;
import com.jty.response.ResponseResult;
import com.jty.service.impl.UserDetailsServiceImpl;
import com.jty.utils.BeanCopyUtils;
import com.jty.utils.JwtUtil;
import com.jty.utils.RedisCache;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity// 开启网络安全注解
@RequiredArgsConstructor
public class SecurityConfig {

    @Resource
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private RedisCache redisCache;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 关闭csrf
                .csrf().disable();
        http
                // 不通过Session获取SecurityContext
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .formLogin()
        // .usernameParameter("username")
        // .passwordParameter("password")
        ;
        http
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .requestMatchers("/login")
                .anonymous()
                // 除上面外的所有请求全部需要认证可访问
                .anyRequest()
                .authenticated();

        http
                // 允许跨域
                .cors();
        http.addFilterBefore(jsonLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        // 把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        ProviderManager pm = new ProviderManager(daoAuthenticationProvider);
        return pm;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JsonLoginFilter jsonLoginFilter() {
        JsonLoginFilter filter = new JsonLoginFilter();
        filter.setAuthenticationSuccessHandler((req, resp, auth) -> {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();


            // 获取userid 生成token
            LoginUser loginUser = (LoginUser) auth.getPrincipal();
            User user = loginUser.getUser();
            String userId = loginUser.getUser().getId().toString();

            String jwtToken = JwtUtil.createJWT(userId);

            // 把用户信息存入redis
            redisCache.setCacheObject("bloglogin:" + userId, loginUser);

            // 把User转换成UserInfoVo
            UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

            // 把token和userInfoVo封装 返回
            BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwtToken, userInfoVo);
            ResponseResult result = ResponseResult.okResult(blogUserLoginVo);

            out.write(JSON.toJSONString(result));

            // //获取当前登录成功的用户对象
            // User user = (User) auth.getPrincipal();
            // user.setPassword(null);
            // ResponseResult result = ResponseResult.okResult("登录成功", user);
            // out.write(new ObjectMapper().writeValueAsString(result));
        });
        filter.setAuthenticationFailureHandler((req, resp, e) -> {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            e.printStackTrace();

            ResponseResult result = ResponseResult.errorResult("用户名或者密码输入错误，登录失败");
            // if (e instanceof BadCredentialsException) {
            //     result.setMsg("用户名或者密码输入错误，登录失败");
            // } else if (e instanceof DisabledException) {
            //     result.setMsg("账户被禁用，登录失败");
            // } else if (e instanceof CredentialsExpiredException) {
            //     result.setMsg("密码过期，登录失败");
            // } else if (e instanceof AccountExpiredException) {
            //     result.setMsg("账户过期，登录失败");
            // } else if (e instanceof LockedException) {
            //     result.setMsg("账户被锁定，登录失败");
            // }
            out.write(JSON.toJSONString(result));
        });
        filter.setAuthenticationManager(authenticationManager());

        filter.setFilterProcessesUrl("/login");
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        return filter;
    }
}