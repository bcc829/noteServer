package com.rabbitcat.note.service.member

import com.rabbitcat.note.domain.member.Member
import com.rabbitcat.note.domain.member.MemberOauth2UserInfo

interface MemberService {
    fun loginMember(id: String, password: String):String
    fun getMemberInfo(id: String): MemberOauth2UserInfo?
    fun getMemberAll():List<Member>?
    fun addMember(member: Member):Member?
    fun updateMember(id:String, updateUserInfo: Map<String, String>):MemberOauth2UserInfo?
    fun deleteMember(id: String)
}