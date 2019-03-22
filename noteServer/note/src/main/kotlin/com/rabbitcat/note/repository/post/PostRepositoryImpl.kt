package com.rabbitcat.note.repository.post

import com.querydsl.jpa.JPQLQuery
import com.querydsl.jpa.impl.JPAQuery
import com.rabbitcat.note.common.enum.PostSearchType
import com.rabbitcat.note.domain.post.Post
import com.rabbitcat.note.domain.post.QPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport


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
                query = from(qPost).where(qPost.deleteFlag.eq(false)).orderBy(qPost.regDate.desc()).fetchAll()
            }
            PostSearchType.CONTENT -> {
                query = from(qPost).where(qPost.deleteFlag.eq(false).and(qPost.content.likeIgnoreCase("%$value%"))).orderBy(qPost.regDate.desc()).fetchAll()
            }
            PostSearchType.REG_ID -> {
                query = from(qPost).where(qPost.deleteFlag.eq(false).and(qPost.regId.likeIgnoreCase("%$value%"))).orderBy(qPost.regDate.desc()).fetchAll()
            }
            PostSearchType.TITLE -> {
                query = from(qPost).where(qPost.deleteFlag.eq(false).and(qPost.title.likeIgnoreCase("%$value%"))).orderBy(qPost.regDate.desc()).fetchAll()
            }
            PostSearchType.TITLE_OR_CONTENT -> {
                query = from(qPost).where(qPost.deleteFlag.eq(false).and(qPost.title.likeIgnoreCase("%$value%").or(qPost.content.likeIgnoreCase("%$value%")))).orderBy(qPost.regDate.desc()).fetchAll()
            }
        }

        val posts = querydsl?.applyPagination(pageable, query)?.fetch()

        return PageImpl<Post>(posts!!, pageable, query.fetchCount())
    }

}