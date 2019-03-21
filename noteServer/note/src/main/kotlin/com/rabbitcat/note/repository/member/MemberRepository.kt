package com.rabbitcat.note.repository.member

import com.rabbitcat.note.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Number> {
    fun findByIdEquals(id: String): Member?
    fun findByIdAndPassword(id:String, password:String): Member?
}