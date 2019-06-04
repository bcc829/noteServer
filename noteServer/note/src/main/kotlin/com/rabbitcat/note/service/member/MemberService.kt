package com.rabbitcat.note.service.member

import com.rabbitcat.note.domain.member.Member

interface MemberService {
    fun loginMember(id: String, password: String):String
    fun getMemberInfo(id: String): Member?
    fun getMemberAll():List<Member>?
    fun addMember(member: Member):Member?
    fun updateMember(id:String ,member: Member):String?
    fun deleteMember(id: String)
}