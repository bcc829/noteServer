package com.rabbitcat.note.service.member

import com.rabbitcat.note.common.util.AuthorizationUtil
import com.rabbitcat.note.domain.idAndPassword.IdAndPassword
import com.rabbitcat.note.domain.member.Member
import com.rabbitcat.note.exception.UnauthorizedException
import com.rabbitcat.note.exception.UserIdDuplicatedException
import com.rabbitcat.note.exception.UserNotExistException
import com.rabbitcat.note.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils



@Service
class MemberServiceImpl: MemberService {

    @Autowired
    lateinit var memberRepository: MemberRepository

    val logger = LoggerFactory.getLogger(javaClass)

    override fun loginMember(id: String, password: String): String{
        var member = memberRepository.findByIdAndPassword(id, password)

        if(member == null) {
            throw UnauthorizedException()
        }
        else{
            var memberIdAndPassword = member.id+":"+member.password

            return Base64Utils.encodeToString(memberIdAndPassword.toByteArray())
        }
    }

    //회원 정보 조회
    override fun getMemberInfo(token: String): Member? {

        var member : Member?

        val tokenId = AuthorizationUtil.getUserNameAndPasswordFromToken(token)[0]

        member = memberRepository.findByIdEquals(tokenId)


        return when(member){
            null -> throw UnauthorizedException()
            else -> member
        }
    }

    //회원 리스트 전부 조회 - 개발에서만 사용
    override fun getMemberAll(): List<Member>? {
        var member: List<Member>?
        try {
            member = memberRepository.findAll()
        }catch (e: Exception){
            logger.error(e.message)
            throw e
        }
        return member
    }

    //회원 가입
    override fun addMember(member: Member): Member? {
        var saveMember: Member?

        saveMember = memberRepository.findByIdEquals(member.id)

        if(saveMember == null){
            saveMember = memberRepository.save(member)
            return saveMember
        }else{
            throw UserIdDuplicatedException()
        }
    }

    //회원 정보 수정
    override fun updateMember(token: String, member: Member): String? {

        var updateMember: Member? = null
        val tokenId = AuthorizationUtil.getUserNameFromToken(token)

        if(tokenId == member.id){
            updateMember = memberRepository.findByIdEquals(member.id)

            updateMember?.address = member.address
            updateMember?.email = member.email
            updateMember?.nickname = member.nickname
            updateMember?.password = member.password
            updateMember?.phoneNumber = member.phoneNumber
        }

        return when(updateMember){
            null -> throw UnauthorizedException()
            else -> {
                updateMember = memberRepository.save(updateMember)
                val memberIdAndPassword = updateMember.id + ":" + updateMember.password
                Base64Utils.encodeToString(memberIdAndPassword.toByteArray())
            }
        }
    }

    override fun deleteMember(token: String) {
        val tokenId = AuthorizationUtil.getUserNameFromToken(token)

        val member = memberRepository.findByIdEquals(tokenId)

        when (member){
            null -> throw UserNotExistException()
            else -> memberRepository.delete(member)
        }
    }
}