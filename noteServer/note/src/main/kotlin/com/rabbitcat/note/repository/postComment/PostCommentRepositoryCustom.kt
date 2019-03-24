package com.rabbitcat.note.repository.postComment

import com.rabbitcat.note.domain.postComment.PostComment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface PostCommentRepositoryCustom  {
    fun getPostCommentWithPaging(postSeqId: Int, pageable: Pageable): Page<PostComment>
    fun getUserPostCommentWithPaging(regId: String, pageable: Pageable): Page<PostComment>
}