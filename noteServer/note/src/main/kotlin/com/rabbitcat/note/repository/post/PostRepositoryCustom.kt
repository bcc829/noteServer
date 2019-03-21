package com.rabbitcat.note.repository.post

import com.rabbitcat.note.common.enum.PostSearchType
import com.rabbitcat.note.domain.post.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface PostRepositoryCustom  {
    fun getPostByUserIdLimitOneOrderByRegDateDesc(regId: String): Post?
    fun getPagingPostWithSearch(postSearchType: PostSearchType, value: String, pageable: Pageable): Page<Post>
}