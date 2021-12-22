package com.example.ftp_demo.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Version: V1.0    <br/>
 * Datetime:   2021/12/21 1:47   <br/>
 * Description: 登录拦截器
 *
 * @author: chen
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册Sa-Token的路由拦截器
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
                    // 根据 path 路由排除匹配
                    SaRouter.match("/**").notMatch("*.html", "*.css", "*.js").check(r -> System.out.println("静态资源"));
                }))
                .addPathPatterns("/**")
                // 放行登录请求
                .excludePathPatterns("/api/login");

    }
}
