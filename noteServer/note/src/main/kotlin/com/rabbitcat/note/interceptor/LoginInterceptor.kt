package com.rabbitcat.note.interceptor

import com.rabbitcat.note.common.util.AuthorizationUtil
import com.rabbitcat.note.exception.UnauthorizedException
import com.rabbitcat.note.service.member.MemberServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest


@Component
class LoginInterceptor: HandlerInterceptorAdapter() {

    @Autowired
    lateinit var memberService : MemberServiceImpl

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try{
            val token = request.getHeader("Authorization")

            val userIdAndPassword = AuthorizationUtil.getUserIdAndPasswordFromToken(token)
            memberService.loginMember(userIdAndPassword[0], userIdAndPassword[1])
        } catch (e: Exception){
            throw UnauthorizedException()
        }

        return true
    }

}