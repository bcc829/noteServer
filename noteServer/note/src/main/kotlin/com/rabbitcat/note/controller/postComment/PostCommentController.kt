package com.rabbitcat.note.controller.postComment

import com.rabbitcat.note.domain.postComment.PostComment
import com.rabbitcat.note.service.postComment.PostCommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("api/v1/")
class PostCommentController {

    @Autowired
    lateinit var postCommentService: PostCommentService

    @GetMapping("post/postComments/{postSeqId}")
    fun getPostCommentController(@PathVariable postSeqId: Int, pageable: Pageable): ResponseEntity<Any> {
        return ResponseEntity(postCommentService.getPostCommentWithPaging(postSeqId, pageable), HttpStatus.OK)
    }

    @GetMapping("member/postComment")
    fun getUserPostCommentController(principal: Principal, pageable: Pageable): ResponseEntity<Any> {
        return ResponseEntity(postCommentService.getUserPostCommentWithPaging(principal.name.toString(), pageable), HttpStatus.OK)
    }

    @PostMapping("postComment")
    fun addPostCommentController(principal: Principal, @RequestBody postComment: PostComment): ResponseEntity<Any> {
        return ResponseEntity(postCommentService.addPostComment(principal.name.toString(), postComment), HttpStatus.OK)
    }

    @PutMapping("postComment")
    fun updatePostCommentController(principal: Principal, @RequestBody postComment: PostComment): ResponseEntity<Any> {
        return ResponseEntity(postCommentService.updatePostComment(principal.name.toString(), postComment), HttpStatus.OK)
    }

    @DeleteMapping("postComment")
    fun deletePostCommentController(principal: Principal, @RequestBody postComment: PostComment): ResponseEntity<Any> {
        return ResponseEntity(postCommentService.deletePostComment(principal.name.toString(), postComment), HttpStatus.OK)
    }
}