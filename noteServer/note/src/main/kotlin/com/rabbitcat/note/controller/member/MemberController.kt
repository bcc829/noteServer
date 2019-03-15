package com.rabbitcat.note.controller.member

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.rabbitcat.note.domain.member.Member
import com.rabbitcat.note.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/")
class MemberController {

    @Autowired
    lateinit var memberRepository: MemberRepository

    val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("member/{id}")
    fun getMemberInfoById(@PathVariable id : String): ResponseEntity<Any> {

        logger.info(">>>>>>>>>>>> getMemberInfoById start!!!!!")

        var member: Member? = null

        try {
            member = memberRepository.findByIdEquals(id)
        }catch (e:Exception){
            logger.error(e.message)
            return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Member is not exist"), HttpStatus.BAD_REQUEST)
        }
        logger.info(">>>>>>>>>>>> getMemberInfoById end!!!!!")

        return ResponseEntity(member, HttpStatus.OK)
    }

    @GetMapping("member")
    fun getMemberInfoAll(): ResponseEntity<Any> {

        logger.info(">>>>>>>>>>>> getMemberInfoAll start!!!!!")

        var memberList : MutableList<Member>? = null

        try {
            memberList = memberRepository.findAll()
        }catch (e: Exception){
            logger.error(e.message)
            return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Error in DB"), HttpStatus.INTERNAL_SERVER_ERROR)
        }

        logger.info(">>>>>>>>>>>> getMemberInfoAll end!!!!!")

        return ResponseEntity(memberList, HttpStatus.OK)
    }

    @PostMapping("member/{id}")
    fun insertMember(@PathVariable id : String, @RequestBody member: Member) : ResponseEntity<Any> {


        var memberSave: Member = member

        try{
            memberRepository.findByIdEquals(id)
            return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Member id is duplicated"), HttpStatus.BAD_REQUEST)
        } catch (e: Exception){
            try{
                memberSave = memberRepository.save(member)
            }catch (e: Exception){
                logger.error(e.message)
                return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Error in DB"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(memberSave, HttpStatus.OK)
    }

    @PutMapping("member/{id}")
    fun updateMember(@PathVariable id : String, @RequestBody member: Member) : ResponseEntity<Any> {

        try{
            var existMember: Member

            try{
                existMember = memberRepository.findByIdEquals(id)
            } catch (e: Exception){
                return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "This member is not exist"), HttpStatus.BAD_REQUEST)
            }

            existMember.password = member.password
            existMember.address = member.address
            existMember.email = member.email
            existMember.nickname = member.nickname
            existMember.phoneNumber = member.phoneNumber

            memberRepository.save(existMember)
            return ResponseEntity(memberRepository.save(existMember), HttpStatus.OK)

        } catch (e: Exception){
            logger.error(e.message)
            return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Error in DB"), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

}