package com.rabbitcat.note.handlers.member


import com.rabbitcat.note.domain.member.Member
import com.rabbitcat.note.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono



@Component
class MemberHandler {

    @Autowired
    lateinit var memberRepository: MemberRepository

    val logger = LoggerFactory.getLogger(javaClass)

     fun getMemberInfoById(request: ServerRequest): Mono<ServerResponse> {

        logger.info(">>>>>>>>>>>> getMemberInfoById start!!!!!")

        val id = request.pathVariable("id")

        val member = memberRepository.findByIdEquals(id)

        logger.info(">>>>>>>>>>>> getMemberInfoById end!!!!!")

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(member), Member::class.java)
    }

    fun getMemberInfoAll(request: ServerRequest): Mono<ServerResponse> {
        logger.info(">>>>>>>>>>>> getMemberInfoAll start!!!!!")

        val memberList = memberRepository.findAll()

        val memberFlux: Flux<Member> = Flux.fromIterable(memberList)

        logger.info(">>>>>>>>>>>> getMemberInfoAll end!!!!!")

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(memberFlux, Member::class.java)
    }

//    fun insertMember(request: ServerRequest): Mono<ServerResponse> {
//        logger.info(">>>>>>>>>>>> getMemberInfoAll start!!!!!")
//
//        val id :String = request.pathVariable("id")
//
//        val memberMono: Mono<Member> = request.bodyToMono<Member>()
//
//        val existMember: Mono<Member> = memberRepository.findByIdEquals(id).toMono()
//
//        return existMember.map { member ->  ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(Mono.just("Id is duplicated"), String::class.java)}
//
//
//        return existMember.map { member -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(memberRepository.save(member)), Member::class.java).block() }
//
//        return memberMono.map { member -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(memberRepository.save(member)), Member::class.java) }
//
//        try {
//            memberRepository.findByIdEquals(id)
//        } catch (e : Exception){
//            return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(memberRepository.save(member)), Member::class.java)
//        }
//
//        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(Mono.just(member), Member::class.java)
//
//    }

}