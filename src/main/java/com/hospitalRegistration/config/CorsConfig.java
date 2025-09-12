package com.hospitalRegistration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();

        // 允许跨域的源地址，生产环境建议指定具体域名
        config.addAllowedOriginPattern("*");

        // 允许跨域携带cookie
        config.setAllowCredentials(true);

        // 允许的请求方法
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        // 允许的请求头
        config.addAllowedHeader("*");

        // 暴露的响应头（前端需要获取的自定义头）
        config.addExposedHeader("token");
        config.addExposedHeader("Content-Disposition");

        // 预检请求有效期（秒）
        config.setMaxAge(3600L);

        // 2. 添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有接口生效
        source.registerCorsConfiguration("/**", config);

        // 3. 返回CorsFilter实例
        return new CorsFilter(source);
    }
}
