package com.rabbitcat.note.configure

import com.rabbitcat.note.interceptor.LoginInterCepter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {

    @Autowired
    lateinit var loginInterCepter:LoginInterCepter

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loginInterCepter)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**/member/all")
                .excludePathPatterns("/api/**/login")
                .excludePathPatterns("/ap/**/member/add")
    }
}