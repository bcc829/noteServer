package com.rabbitcat.note.domain.member

import com.mysema.query.annotations.QueryEntity
import org.hibernate.annotations.DynamicInsert
import java.util.*
import javax.persistence.*

@Entity
@QueryEntity
@Table(name = "member", schema = "public")
@DynamicInsert
data class Member(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val seqId : Integer? = null,
        val id: String,
        var password: String,
        var phoneNumber: String,
        var address: String,
        var nickname: String,
        var email: String,
        val regDate: Date? = Date()) {

}