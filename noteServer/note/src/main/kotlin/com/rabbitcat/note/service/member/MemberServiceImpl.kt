package com.rabbitcat.note.service.member

import com.rabbitcat.note.common.enum.UpdateUserInfoParameter
import com.rabbitcat.note.common.util.AuthorizationUtil
import com.rabbitcat.note.common.util.ValidationUtil
import com.rabbitcat.note.domain.idAndPassword.IdAndPassword
import com.rabbitcat.note.domain.member.Member
import com.rabbitcat.note.domain.member.MemberOauth2UserInfo
import com.rabbitcat.note.exception.InvalidArgumentException
import com.rabbitcat.note.exception.UnauthorizedException
import com.rabbitcat.note.exception.UserIdDuplicatedException
import com.rabbitcat.note.exception.UserNotExistException
import com.rabbitcat.note.repository.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import javax.transaction.Transactional


@Service
class MemberServiceImpl: MemberService {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    private val logger = LoggerFactory.getLogger(javaClass)

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
    override fun getMemberInfo(id: String): MemberOauth2UserInfo? {

        var member  = memberRepository.findByIdEquals(id)

        return when(member){
            null -> throw UnauthorizedException()
            else -> MemberOauth2UserInfo(id = member.id, nickname = member.nickname, email = member.email)
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
            if(ValidationUtil.isEmailFormat(member.id)){
                saveMember = memberRepository.save(member)
                return saveMember
            }else {
                throw InvalidArgumentException("${member.id} isn't email format")
            }
        }else{
            throw UserIdDuplicatedException()
        }
    }

    //회원 정보 수정
    @Transactional
    override fun updateMember(id: String, updateUserInfo: Map<String, String>): MemberOauth2UserInfo? {

        var updateMember = memberRepository.findByIdEquals(id)!!

        updateUserInfo.forEach { key, value  ->
            run {
                when (key.toLowerCase()) {
                    UpdateUserInfoParameter.ADDRESS.name.toLowerCase() -> updateMember.address = value
                    UpdateUserInfoParameter.EMAIL.name.toLowerCase() -> {
                        if(ValidationUtil.isEmailFormat(email = value)){
                            updateMember.email = value
                        } else {
                            throw InvalidArgumentException()
                        }
                    }
                    UpdateUserInfoParameter.NICKNAME.name.toLowerCase() -> updateMember.nickname = value
                    UpdateUserInfoParameter.PASSWORD.name.toLowerCase() -> updateMember.password = passwordEncoder.encode(value)
                    UpdateUserInfoParameter.PHONE_NUMBER.name.toLowerCase() -> updateMember.phoneNumber = value
                }
            }
        }

        updateMember = memberRepository.save(updateMember)

        return MemberOauth2UserInfo(id = updateMember.id, nickname = updateMember.nickname, email = updateMember.email)
    }

    @Transactional
    override fun deleteMember(id: String) {
        val member = memberRepository.findByIdEquals(id)!!

        memberRepository.delete(member)
    }
}