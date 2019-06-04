package com.rabbitcat.note.controller.post

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.rabbitcat.note.common.enum.PostSearchType
import com.rabbitcat.note.domain.post.Post
import com.rabbitcat.note.service.post.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("api/v1/")
class PostController{

    @Autowired
    lateinit var postService: PostService

    @GetMapping("post/newly")
    fun getNewlyUserPostController(principal: Principal): ResponseEntity<Any> {
        return ResponseEntity(postService.getUserNewlyPostLimitOne(principal.name.toString()), HttpStatus.OK)
    }

    @GetMapping("post/{seqId}")
    fun getPostBySeqIdController(@PathVariable seqId: Int): ResponseEntity<Any> {
        return ResponseEntity(postService.getPost(seqId), HttpStatus.OK)
    }

    @PostMapping("post")
    fun addPostController(principal: Principal, @RequestBody post: Post): ResponseEntity<Any> {
        return ResponseEntity(postService.addPost(principal.name.toString(), post),  HttpStatus.OK)
    }

    @PutMapping("post")
    fun updatePostController(principal: Principal, @RequestBody post: Post): ResponseEntity<Any> {
        return ResponseEntity(postService.updatePost(principal.name.toString(), post), HttpStatus.OK)
    }

    @DeleteMapping("post/{seqId}")
    fun deletePostController(principal: Principal, @PathVariable seqId: Int): ResponseEntity<Any> {
        postService.deletePost(principal.name.toString(), seqId)
        return ResponseEntity(JsonNodeFactory.instance.objectNode().put("success_message", "Post delete success"), HttpStatus.OK)
    }

    @GetMapping("posts")
    fun searchPostWithPageableController(@RequestParam(required = false, name = "type", defaultValue = "ALL") type: PostSearchType,
                               @RequestParam(required = false, name = "value", defaultValue = "") value: String,
                               pageable: Pageable): ResponseEntity<Any> {
        return ResponseEntity(postService.getPostWithPageableAndSearchType(type, value, pageable), HttpStatus.OK)
    }

}