package com.rabbitcat.note.domain.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Number> {
    fun findBySeqIdEquals(seqId: Int): Member
    fun findByIdEquals(id: String): Member
}