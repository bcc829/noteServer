package com.rabbitcat.note.handlers.memberHandler


import com.rabbitcat.note.domain.member.Member
import com.rabbitcat.note.domain.member.MemberRepository
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

    fun insertMember(request: ServerRequest): Mono<ServerResponse> {

        logger.info(">>>>>>>>>>>> getInsertMember start!!!!!")

        val bodyToMono: Mono<Member> = request.bodyToMono(Member::class.java)

        val member: Member? = bodyToMono.block()

        if(member == null) {
            return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(Mono.just("Member data is required"), String::class.java)
        }else{
            try{

                if(memberRepository.findByIdEquals(member.id) != null){
                    logger.info(">>>>>>>>>>>> memberId duplication!!!!!")
                    return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(Mono.just("Member id duplication"), String::class.java)
                }
                memberRepository.save(member)

            } catch (e : Exception){
                return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(Mono.just("Error in DB"), String::class.java)
            }
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just("Member data insert OK"), String::class.java)
        }

    }
}