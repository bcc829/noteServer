package com.rabbitcat.note.repository.post

import com.rabbitcat.note.domain.post.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface PostRepository : JpaRepository<Post, Number>{
    fun getUserByLimitOneOrderByRegDateDescByQuerydsl(seqId: Int): Post?
    fun getUserListByQuerydsl(pageable: Pageable): Page<Post>?
}