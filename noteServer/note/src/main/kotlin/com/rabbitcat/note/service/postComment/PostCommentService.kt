package com.rabbitcat.note.service.postComment

import com.rabbitcat.note.domain.postComment.PostComment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostCommentService {
    fun getPostCommentWithPaging(postSeqId: Int, pageable: Pageable): Page<PostComment>
    fun getUserPostCommentWithPaging(id: String, pageable: Pageable): Page<PostComment>
    fun addPostComment(id: String, postComment: PostComment): PostComment
    fun updatePostComment(id: String, postComment: PostComment): PostComment
    fun deletePostComment(id: String, postComment: PostComment)
}