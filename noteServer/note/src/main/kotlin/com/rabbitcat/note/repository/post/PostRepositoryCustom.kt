package com.rabbitcat.note.repository.post

import com.rabbitcat.note.domain.post.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostRepositoryCustom  {
    fun getUserByLimitOneOrderByRegDateDescByQuerydsl(seqId: Int): Post?
}