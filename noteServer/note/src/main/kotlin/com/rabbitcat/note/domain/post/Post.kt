package com.rabbitcat.note.domain.post

import com.mysema.query.annotations.QueryEntity
import org.hibernate.annotations.DynamicInsert
import java.util.*
import javax.persistence.*

@Entity
@QueryEntity
@Table(name = "post", schema = "public")
@DynamicInsert
data class Post(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        val seqId : Integer? = null,
        var title : String,
        var content : String,
        val regDate : Date = Date(),
        var updDate : Date,
        var delDate : Date,
        var delYn : String
) {
}