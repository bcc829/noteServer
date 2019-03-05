package com.rabbitcat.note.repository.post

import com.querydsl.jpa.JPQLQuery
import com.rabbitcat.note.domain.post.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import com.querydsl.jpa.impl.JPAQuery
import com.rabbitcat.note.domain.post.QPost
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import javax.persistence.EntityManager


class PostRepositoryImpl :PostRepositoryCustom, QuerydslRepositorySupport(PostRepositoryImpl::class.java) {

    override fun getUserByLimitOneOrderByRegDateDescByQuerydsl(seqId: Int): Post?{

        val qPost = QPost.post
        val query = JPAQuery<Post>(entityManager)

        return query.from(qPost).where(qPost.seqId.eq(seqId)).limit(1).orderBy(qPost.regDate.desc()).fetchOne()
    }

}