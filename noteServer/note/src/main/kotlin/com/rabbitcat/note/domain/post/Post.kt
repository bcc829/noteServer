package com.rabbitcat.note.domain.post

import com.querydsl.core.annotations.QueryEntity
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
        val seqId : Int? = null,
        var title : String,
        var content : String,
        var regDate : Date? = null,
        var updDate : Date? = null,
        var delDate : Date? = null,
//        @ManyToOne(targetEntity = Member::class, fetch = FetchType.LAZY)
//        @JoinColumn(name="reg_id", referencedColumnName = "id")
//        @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
//        val member: Member? = null,
        var regId : String,
        var readCount: Int? = null,
        var deleteFlag : Boolean? = null
)