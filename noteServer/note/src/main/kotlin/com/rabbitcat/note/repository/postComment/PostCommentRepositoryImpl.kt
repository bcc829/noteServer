package com.rabbitcat.note.repository.postComment

import com.querydsl.jpa.impl.JPAQuery
import com.rabbitcat.note.domain.postComment.PostComment
import com.rabbitcat.note.domain.postComment.QPostComment
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport


class PostCommentRepositoryImpl : PostCommentRepositoryCustom, QuerydslRepositorySupport(PostCommentRepositoryImpl::class.java) {
    override fun getPostCommentWithPaging(postSeqId: Int, pageable: Pageable): Page<PostComment> {
        val qPostComment = QPostComment.postComment
        var query = JPAQuery<PostComment>(entityManager)

        query = query.from(qPostComment).where(qPostComment.postSeqId.eq(postSeqId)).orderBy(qPostComment.regDate.asc()).fetchAll()

        val postComments = querydsl?.applyPagination(pageable, query)?.fetch()

        return PageImpl<PostComment>(postComments!!, pageable, query.fetchCount())
    }

    override fun getUserPostCommentWithPaging(regId: String, pageable: Pageable): Page<PostComment> {

        val qPostComment = QPostComment.postComment
        var query = JPAQuery<PostComment>(entityManager)

        query = query.from(qPostComment).where(qPostComment.regId.eq(regId)).orderBy(qPostComment.regDate.asc()).fetchAll()

        val postComments = querydsl?.applyPagination(pageable, query)?.fetch()

        return PageImpl<PostComment>(postComments!!, pageable, query.fetchCount())
    }

}