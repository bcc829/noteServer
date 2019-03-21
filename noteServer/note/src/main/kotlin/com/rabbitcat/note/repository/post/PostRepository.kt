package com.rabbitcat.note.repository.post

import com.rabbitcat.note.domain.post.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Number>, PostRepositoryCustom {
    fun findBySeqId(seqId: Int): Post
    fun findByTitle(title: String): Post?
}