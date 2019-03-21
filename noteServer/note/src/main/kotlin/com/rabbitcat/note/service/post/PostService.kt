package com.rabbitcat.note.service.post

import com.rabbitcat.note.common.enum.PostSearchType
import com.rabbitcat.note.domain.post.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostService {
    fun getUserNewlyPostLimitOne(token: String): Post?
    fun getPostWithPageableAndSearchType(type: PostSearchType = PostSearchType.ALL, value: String, pageable: Pageable): Page<Post>
    fun addPost(token: String, post: Post): Post
    fun updatePost(token: String, post: Post): Post
    fun getPost(seqId: Int): Post
    fun deletePost(token: String, seqId: Int)
}