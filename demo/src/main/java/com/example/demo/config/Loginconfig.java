/**
package com.example.demo.config;

import com.example.demo.interceptor.Logininterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Loginconfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Logininterceptor())
                .excludePathPatterns("/error")
                .excludePathPatterns("/login")
                .addPathPatterns("/**");
    }
}
 */
