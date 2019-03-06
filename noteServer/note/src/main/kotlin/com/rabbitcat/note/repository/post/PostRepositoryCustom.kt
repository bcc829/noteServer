package com.rabbitcat.note.repository.post

import com.rabbitcat.note.domain.post.Post

interface PostRepositoryCustom  {
    fun getUserByLimitOneOrderByRegDateDescByQuerydsl(seqId: Int): Post?
}