package com.rabbitcat.note.repository.post

import com.querydsl.jpa.JPQLQuery
import com.querydsl.jpa.impl.JPAQuery
import com.rabbitcat.note.common.enum.PostSearchType
import com.rabbitcat.note.domain.post.Post
import com.rabbitcat.note.domain.post.QPost
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.awt.print.Pageable


class PostRepositoryImpl : PostRepositoryCustom, QuerydslRepositorySupport(PostRepositoryImpl::class.java) {

    override fun getPostByUserIdLimitOneOrderByRegDateDesc(regId: String): Post? {
        val qPost = QPost.post
        val query = JPAQuery<Post>(entityManager)

        return query.from(qPost).where(qPost.regId.eq(regId)).limit(1).orderBy(qPost.regDate.desc()).fetchOne()
    }

    override fun getPagingPostWithSearch(postSearchType: PostSearchType, value: String, pageable: Pageable): Page<Post> {
        val qPost = QPost.post
        var query:JPQLQuery<Post>

        when(postSearchType){
            PostSearchType.ALL -> {
                query = from(qPost).fetchAll()
            }
            PostSearchType.CONTENT ->{
                query = from(qPost).where(qPost.content.likeIgnoreCase("%$value%"))
            }
        }
    }

}