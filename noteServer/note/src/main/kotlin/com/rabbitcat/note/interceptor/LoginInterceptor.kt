package com.rabbitcat.note.interceptor

import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter


@Component
class LoginInterceptor: HandlerInterceptorAdapter() {

//    @Autowired
//    lateinit var memberService : MemberServiceImpl
//
//    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
//        try{
//            val token = request.getHeader("Authorization")
//
//            val userIdAndPassword = AuthorizationUtil.getUserIdAndPasswordFromToken(token)
//            memberService.loginMember(userIdAndPassword[0], userIdAndPassword[1])
//        } catch (e: Exception){
//            throw UnauthorizedException()
//        }
//
//        return true
//    }

}