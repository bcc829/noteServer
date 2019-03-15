package com.rabbitcat.note.repository.post

import com.querydsl.jpa.impl.JPAQuery
import com.rabbitcat.note.domain.post.Post
import com.rabbitcat.note.domain.post.QPost
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport


class PostRepositoryImpl : PostRepositoryCustom, QuerydslRepositorySupport(PostRepositoryImpl::class.java) {

    override fun getPostByUserIdLimitOneOrderByRegDateDesc(regId: String): Post? {
        val qPost = QPost.post
        val query = JPAQuery<Post>(entityManager)

        return query.from(qPost).where(qPost.member.id.eq(regId)).limit(1).orderBy(qPost.regDate.desc()).fetchOne()
    }

}