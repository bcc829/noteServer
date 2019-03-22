package com.rabbitcat.note.domain.post

import com.rabbitcat.note.common.enum.PostSearchType
import com.rabbitcat.note.repository.post.PostRepository
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class PostRepositoryTest {

    @Autowired
   lateinit var postRepository : PostRepository

    @Before
    fun cleanUpPost(){
        val post = postRepository.findByTitle("Junit &^*&^*&(^*^&*^(*Repository Test")

        if(post != null)
            postRepository.delete(post)
    }

    @Test
    fun insert_member_test(){
        val post = Post(title = "Junit &^*&^*&(^*^&*^(*Repository Test", content = "my test", regId = "jeong")

        assertEquals("Junit &^*&^*&(^*^&*^(*Repository Test", postRepository.save(post).title)
    }

    @Test
    fun select_post_data_by_seq_id(){
        val post = postRepository.findByTitle("Junit &^*&^*&(^*^&*^(*Repository Test")

        assertEquals("Junit &^*&^*&(^*^&*^(*Repository Test", postRepository.findBySeqId(post?.seqId!!).title)
    }

    @Test
    fun select_user_newly_post(){
        val post = postRepository.getPostByUserIdLimitOneOrderByRegDateDesc("정준영")

        assertEquals("정준영", post?.regId)
    }

    @Test
    fun select_post_pageable(){
        val postList = postRepository.getPagingPostWithSearch(postSearchType = PostSearchType.ALL, value = "", pageable = Pageable.unpaged())

        assertEquals(false, postList.isEmpty)
    }

    @After
    fun cleanUpPostAfter(){
        val post = postRepository.findByTitle("Junit Repository Test")

        if(post != null)
            postRepository.delete(post)
    }
}