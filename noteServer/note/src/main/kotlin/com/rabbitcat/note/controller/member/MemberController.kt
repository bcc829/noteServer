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
import java.security.Principal


@RestController
@RequestMapping("api/v1/")
class MemberController {

    @Autowired
    lateinit var memberService: MemberServiceImpl

//    @PostMapping("login")
//    fun memberSignUp(@RequestBody idAndPassword: IdAndPassword):ResponseEntity<Any>{
//        val token = memberService.loginMember(idAndPassword.id, idAndPassword.password)
//        return ResponseEntity(token, HttpStatus.OK)
//    }

    @GetMapping("member")
    fun getMemberInfoById(principal: Principal): ResponseEntity<Any> {

        var member = memberService.getMemberInfo(principal.name.toString())

       return ResponseEntity(member, HttpStatus.OK)
    }

//    @GetMapping("member/all")
//    fun getMemberInfoAll(): ResponseEntity<Any> {
//
//        var memberList : List<Member>?
//
//        try {
//            memberList = memberService.getMemberAll()
//        }catch (e: Exception){
//            logger.error(e.message)
//            return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Error in DB"), HttpStatus.INTERNAL_SERVER_ERROR)
//        }
//
//        return when(memberList?.isEmpty()){
//            true -> ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Member is not exist"), HttpStatus.BAD_REQUEST)
//
//            false -> ResponseEntity(memberList, HttpStatus.OK)
//
//            else -> ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Member is not exist"), HttpStatus.BAD_REQUEST)
//        }
//    }

//    @PostMapping("join")
//    fun insertMember(@RequestBody member: Member) : ResponseEntity<Any> {
//
//        val memberSave = memberService.addMember(member)
//
//        return ResponseEntity(memberSave, HttpStatus.OK)
//
//    }

    @PutMapping("member")
    fun updateMember(principal: Principal, @RequestBody updateInfo: Map<String, String>) : ResponseEntity<Any> {

        val updateMember = memberService.updateMember(principal.name.toString(), updateInfo)

        return ResponseEntity(updateMember, HttpStatus.OK)
    }

    @DeleteMapping("member")
    fun deleteMember(principal: Principal): ResponseEntity<Any> {
        memberService.deleteMember(principal.name.toString())
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("success_message", "Withdrawal Success"), HttpStatus.OK)
    }
}