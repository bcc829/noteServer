package com.rabbitcat.note.repository.postComment

import com.rabbitcat.note.domain.postComment.PostComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostCommentRepository : JpaRepository<PostComment, Number>, PostCommentRepositoryCustom{
    fun findBySeqId(seqId: Int): PostComment
}