package com.rabbitcat.note.controller.post

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.rabbitcat.note.domain.post.Post
import com.rabbitcat.note.repository.post.PostRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/")
class PostController{

    @Autowired
    lateinit var PostRepository: PostRepository

    val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @RequestMapping("post/newly/{memberId}")
    fun getNewlyUserPost(@PathVariable memberId:String): ResponseEntity<Any>{

        var post :Post? = null

        try {
            post = PostRepository.getPostByUserIdLimitOneOrderByRegDateDesc(memberId)
        }catch (e: Exception){
            logger.error(e.message)
            return ResponseEntity(JsonNodeFactory.instance.objectNode().put("errorMsg", "Error in DB"), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(post, HttpStatus.OK)
    }

}