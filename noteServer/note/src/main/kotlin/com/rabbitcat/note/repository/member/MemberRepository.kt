package com.rabbitcat.note.repository.member

import com.rabbitcat.note.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Number> {
    fun findBySeqIdEquals(seqId: Int): Member
    fun findByIdEquals(id: String): Member
}