package com.rabbitcat.note.domain.post

import com.rabbitcat.note.repository.member.MemberRepository
import com.rabbitcat.note.repository.post.PostRepositoryImpl
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
   lateinit var postRepository : PostRepositoryImpl

    @After
    fun cleanUp(){

    }

    @Test
    fun select_member_data_by_seq_id(){
       val post = postRepository.getUserByLimitOneOrderByRegDateDescByQuerydsl(2);
       println(post.toString())

        assertEquals("jeong", post?.regId)
    }

}