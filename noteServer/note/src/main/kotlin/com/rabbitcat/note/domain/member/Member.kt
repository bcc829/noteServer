package com.rabbitcat.note.domain.member

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "member", schema = "public")
data class Member(
        @Id
        @GeneratedValue
        val seqId : Integer,
        val id: String,
        val password: String,
        val phoneNumber: Integer,
        val address: String,
        val nickname: String,
        val email: String,
        val regDate: Date) {

}