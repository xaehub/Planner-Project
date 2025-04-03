package com.example.planner.global.config;

import com.example.planner.sign.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    // LoginFilter를 등록
    @Bean
    public FilterRegistrationBean loginFilter() {

        //필터를 등록하고, 순서와 url 패턴 설정
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());
        // 1번 필터
        filterRegistrationBean.setOrder(1);
        // 모든 url("/*")에 적용하는 필터
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
