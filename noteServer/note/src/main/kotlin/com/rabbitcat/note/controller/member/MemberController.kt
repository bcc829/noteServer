package com.rabbitcat.note.controller.member

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.rabbitcat.note.domain.member.Member
import com.rabbitcat.note.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@RestController
class MemberController {

    @Autowired
    lateinit var memberRepository: MemberRepository

    val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/api/v1/member/{id}")
    fun insertMember(@PathVariable id : String, @RequestBody member: Member) : Mono<ResponseEntity<Any>> {

        logger.info("##############insertMember Controller start")

        try{
            memberRepository.findByIdEquals(id)
            return ResponseEntity<Any>(JsonNodeFactory.instance.objectNode().put("errorMsg", "Member id is duplicated"), HttpStatus.BAD_REQUEST).toMono()
        } catch (e: Exception){

            try{
                memberRepository.save(member)
                return ResponseEntity<Any>(memberRepository.save(member), HttpStatus.OK).toMono()
            }catch (e: Exception){
                logger.error(e.message)
                return ResponseEntity<Any>(JsonNodeFactory.instance.objectNode().put("errorMsg", "Error in DB"), HttpStatus.INTERNAL_SERVER_ERROR).toMono()
            }
        }

    }

    @PutMapping("/api/v1/member/{id}")
    fun updateMember(@PathVariable id : String, @RequestBody member: Member) : Mono<ResponseEntity<Any>> {

        logger.info("##############updateMember Controller start")

        try{

            var existMember: Member;

            try{
                existMember = memberRepository.findByIdEquals(id)
            } catch (e: Exception){
                return ResponseEntity<Any>(JsonNodeFactory.instance.objectNode().put("errorMsg", "This member is not exist"), HttpStatus.BAD_REQUEST).toMono()
            }

            existMember.password = member.password
            existMember.address = member.address
            existMember.email = member.email
            existMember.nickname = member.nickname
            existMember.phoneNumber = member.phoneNumber

            memberRepository.save(existMember)
            return ResponseEntity<Any>(memberRepository.save(existMember), HttpStatus.OK).toMono()

        } catch (e: Exception){
            logger.error(e.message)
            return ResponseEntity<Any>(JsonNodeFactory.instance.objectNode().put("errorMsg", "Error in DB"), HttpStatus.INTERNAL_SERVER_ERROR).toMono()
        }

    }

}