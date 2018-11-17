package com.rabbitcat.note.controller.member

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.rabbitcat.note.domain.member.Member
import com.rabbitcat.note.domain.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@RestController
class MemberController {

    @Autowired
    lateinit var memberRepository: MemberRepository

    val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/api/v1/member/{id}")
    fun insertMember(@PathVariable id : String, @RequestBody member: Member) : Mono<ResponseEntity<Any>> {

        try{
            memberRepository.findByIdEquals(id)
            return ResponseEntity<Any>(JsonNodeFactory.instance.objectNode().put("errorMsg", "member id is duplicated"), HttpStatus.BAD_REQUEST).toMono()
        } catch (e: Exception){

            try{
                memberRepository.save(member)
                return ResponseEntity<Any>(memberRepository.save(member), HttpStatus.OK).toMono()
            }catch (e: Exception){
                logger.error(e.message)
                return ResponseEntity<Any>(JsonNodeFactory.instance.objectNode().put("errorMsg", "error in DB"), HttpStatus.INTERNAL_SERVER_ERROR).toMono()
            }
        }

    }

}