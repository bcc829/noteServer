package com.rabbitcat.note.domain.member

import org.hibernate.annotations.DynamicInsert
import reactor.core.publisher.Mono
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "member", schema = "public")
@DynamicInsert
data class Member(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val seqId : Integer? = null,
        val id: String,
        val password: String,
        val phoneNumber: String,
        val address: String,
        val nickname: String,
        val email: String,
        val regDate: Date? = Date()) {

}