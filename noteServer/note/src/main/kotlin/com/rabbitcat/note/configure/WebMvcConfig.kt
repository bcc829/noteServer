package com.rabbitcat.note.configure

import com.rabbitcat.note.interceptor.LoginInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {

    @Autowired
    lateinit var loginInterceptor:LoginInterceptor

//    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/api")
//                .excludePathPatterns("/**/member/all")
//                .excludePathPatterns("/**/login")
//                .excludePathPatterns("/**/join")
//                .excludePathPatterns("/sso")
//                .excludePathPatterns("/oauthCallback")
//    }
}