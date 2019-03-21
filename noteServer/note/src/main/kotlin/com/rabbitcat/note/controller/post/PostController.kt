package com.rabbitcat.note.controller.post

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.rabbitcat.note.common.enum.PostSearchType
import com.rabbitcat.note.domain.post.Post
import com.rabbitcat.note.service.post.PostServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/")
class PostController{

    @Autowired
    lateinit var postService: PostServiceImpl

    @GetMapping("post/newly")
    fun getNewlyUserPostController(@RequestHeader authorization: String): ResponseEntity<Any> {
        return ResponseEntity(postService.getUserNewlyPostLimitOne(authorization), HttpStatus.OK)
    }

    @GetMapping("post/{seqId}")
    fun getPostBySeqIdController(@PathVariable seqId: Int): ResponseEntity<Any> {
        return ResponseEntity(postService.getPost(seqId), HttpStatus.OK)
    }

    @PostMapping("post")
    fun addPostController(@RequestHeader authorization: String, @RequestBody post: Post): ResponseEntity<Any> {
        return ResponseEntity(postService.addPost(authorization, post),  HttpStatus.OK)
    }

    @PutMapping("post")
    fun updatePostController(@RequestHeader authorization: String, @RequestBody post: Post): ResponseEntity<Any> {
        return ResponseEntity(postService.updatePost(authorization, post), HttpStatus.OK)
    }

    @DeleteMapping("post/{seqId}")
    fun deletePostController(@RequestHeader authorization: String, @PathVariable seqId: Int): ResponseEntity<Any> {
        postService.deletePost(authorization, seqId)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("success_message", "Post delete success"), HttpStatus.OK)
    }

    @GetMapping("posts")
    fun searchPostWithPageableController(@RequestParam(required = false, name = "type", defaultValue = "ALL") type: PostSearchType,
                               @RequestParam(required = false, name = "value", defaultValue = "") value: String,
                               pageable: Pageable): ResponseEntity<Any> {
        return ResponseEntity(postService.getPostWithPageableAndSearchType(type, value, pageable), HttpStatus.OK)
    }

}