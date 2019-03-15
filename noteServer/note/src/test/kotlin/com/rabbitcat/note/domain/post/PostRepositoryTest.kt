package com.rabbitcat.note.domain.post

import com.rabbitcat.note.repository.post.PostRepository
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class PostRepositoryTest {

    @Autowired
   lateinit var postRepository : PostRepository

    @Test
    fun select_post_data_by_seq_id(){
        val post = postRepository.getUserByLimitOneOrderByRegDateDescByQuerydsl(2)
        assertEquals("jeong", post?.member?.id)
    }

    @Test
    fun select_post_data_by_reg_id(){
        val post = postRepository.getUserByLimitOneOrderByRegDateDescByQuerydsl(2)
        assertEquals("jeong", post?.member?.id)
    }
}