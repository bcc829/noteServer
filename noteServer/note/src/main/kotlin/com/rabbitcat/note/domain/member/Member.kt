package com.rabbitcat.note.domain.member

import com.querydsl.core.annotations.QueryEntity
import com.rabbitcat.note.common.constant.Constant
import org.hibernate.annotations.DynamicInsert
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*
import javax.persistence.*

@Entity
@QueryEntity
@Table(name = "member", schema = "public")
@DynamicInsert
data class Member(
        val seqId : Int? = null,
        @Id
        val id: String,
        var password: String,
        var phoneNumber: String,
        var address: String,
        var nickname: String,
        var email: String,
        val regDate: Date? = DateTime.now(DateTimeZone.forID(Constant.TIME_ZONE)).toDate(),
        )