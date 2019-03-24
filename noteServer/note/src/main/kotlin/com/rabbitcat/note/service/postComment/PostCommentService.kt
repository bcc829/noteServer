package com.rabbitcat.note.service.postComment

import com.rabbitcat.note.domain.postComment.PostComment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostCommentService {
    fun getPostCommentWithPaging(postSeqId: Int, pageable: Pageable): Page<PostComment>
    fun getUserPostCommentWithPaging(token: String, pageable: Pageable): Page<PostComment>
    fun addPostComment(token: String, postComment: PostComment): PostComment
    fun updatePostComment(token: String, postComment: PostComment): PostComment
    fun deletePostComment(token: String, postComment: PostComment)
}