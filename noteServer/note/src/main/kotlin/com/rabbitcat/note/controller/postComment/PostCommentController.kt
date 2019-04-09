package com.rabbitcat.note.controller.postComment

import com.rabbitcat.note.domain.postComment.PostComment
import com.rabbitcat.note.service.postComment.PostCommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun getUserPostCommentController(@RequestHeader authorization: String, pageable: Pageable): ResponseEntity<Any> {
        return ResponseEntity(postCommentService.getUserPostCommentWithPaging(authorization, pageable), HttpStatus.OK)
    }

    @PostMapping("postComment")
    fun addPostCommentController(@RequestHeader authorization: String, @RequestBody postComment: PostComment): ResponseEntity<Any> {
        return ResponseEntity(postCommentService.addPostComment(authorization, postComment), HttpStatus.OK)
    }

    @PutMapping("postComment")
    fun updatePostCommentController(@RequestHeader authorization: String, @RequestBody postComment: PostComment): ResponseEntity<Any> {
        return ResponseEntity(postCommentService.updatePostComment(authorization, postComment), HttpStatus.OK)
    }

    @DeleteMapping("postComment")
    fun deletePostCommentController(@RequestHeader authorization: String, @RequestBody postComment: PostComment): ResponseEntity<Any> {
        return ResponseEntity(postCommentService.deletePostComment(authorization, postComment), HttpStatus.OK)
    }
}