package com.rabbitcat.note.config.member

import com.rabbitcat.note.handlers.member.MemberHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.RouterFunctions.route

@Configuration
class MeberRoutesConfig {

    @Bean
    fun memberRoutes(memberHandler: MemberHandler) = nest (
        path("/api/v1"),
        route(GET("/member/{id}"), HandlerFunction (memberHandler::getMemberInfoById))
                .andRoute(GET("/member"), HandlerFunction (memberHandler::getMemberInfoAll))

    )
//    fun memberRoutes(@Autowired member: MemberHandler): RouterFunction<ServerResponse>{
//        //TODO
//        return RouterFunctions.route(GET("/people/{id}").and(accept(MediaType.APPLICATION_JSON)), HandlerFunction { member.getMemberInfoById})
//                .andRoute(GET("/people").and(accept(MediaType.APPLICATION_JSON)), member::getMemberInfoAll)
//                .andRoute(PUT("/people/{id}").and(accept(MediaType.APPLICATION_JSON)), member::insertMember)
//
//    }
}