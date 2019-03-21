package com.rabbitcat.note.service.member

import com.rabbitcat.note.domain.member.Member

interface MemberService {
    fun loginMember(id: String, password: String):String
    fun getMemberInfo(token: String): Member?
    fun getMemberAll():List<Member>?
    fun addMember(member: Member):Member?
    fun updateMember(token:String ,member: Member):String?
    fun deleteMeber(token: String)
}