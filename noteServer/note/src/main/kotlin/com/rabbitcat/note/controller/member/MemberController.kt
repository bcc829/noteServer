package com.rabbitcat.note.controller.member

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.rabbitcat.note.domain.idAndPassword.IdAndPassword
import com.rabbitcat.note.domain.member.Member
import com.rabbitcat.note.repository.member.MemberRepository
import com.rabbitcat.note.service.member.MemberService
import com.rabbitcat.note.service.member.MemberServiceImpl
import org.hibernate.exception.SQLGrammarException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/")
class MemberController {

    @Autowired
    lateinit var memberService: MemberServiceImpl

    val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("login")
    fun memberSignUp(@RequestBody idAndPassword: IdAndPassword):ResponseEntity<Any>{
        val token = memberService.loginMember(idAndPassword.id, idAndPassword.password)
        return ResponseEntity(token, HttpStatus.OK)
    }

    @GetMapping("member/{id}")
    fun getMemberInfoById(@PathVariable id: String): ResponseEntity<Any> {

        var member: Member? = null

        memberService.getMemberInfo(id)

       return ResponseEntity(member, HttpStatus.OK)
    }

    @GetMapping("member/all")
    fun getMemberInfoAll(): ResponseEntity<Any> {

        var memberList : List<Member>?

        try {
            memberList = memberService.getMemberAll()
        }catch (e: Exception){
            logger.error(e.message)
            return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Error in DB"), HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return when(memberList?.isEmpty()){
            true -> ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Member is not exist"), HttpStatus.BAD_REQUEST)

            false -> ResponseEntity(memberList, HttpStatus.OK)

            else -> ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Member is not exist"), HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("member/add")
    fun insertMember(@RequestBody member: Member) : ResponseEntity<Any> {

        val memberSave = memberService.addMember(member)

        return ResponseEntity(memberSave, HttpStatus.OK)

    }

    @PutMapping("member/{id}")
    fun updateMember(@RequestHeader token: String, @RequestBody member: Member) : ResponseEntity<Any> {

        var updateMember: Member?

        try {
            updateMember = memberService.updateMember(token, member)
        } catch (e: Exception){
            return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Error in DB"), HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return when(updateMember){
            null -> ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "This member is not exist"), HttpStatus.BAD_REQUEST)
            else -> ResponseEntity(updateMember, HttpStatus.OK)
        }
    }

}