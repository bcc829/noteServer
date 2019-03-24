package com.rabbitcat.note.service.postComment

import com.rabbitcat.note.common.util.AuthorizationUtil
import com.rabbitcat.note.domain.postComment.PostComment
import com.rabbitcat.note.exception.UnauthorizedException
import com.rabbitcat.note.repository.member.MemberRepository
import com.rabbitcat.note.repository.postComment.PostCommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostCommentServiceImpl: PostCommentService {

    @Autowired
    lateinit var postCommentRepository: PostCommentRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun getPostCommentWithPaging(postSeqId: Int, pageable: Pageable): Page<PostComment> {
        return postCommentRepository.getPostCommentWithPaging(postSeqId, pageable)
    }

    override fun getUserPostCommentWithPaging(token: String, pageable: Pageable): Page<PostComment> {
        val memberId = AuthorizationUtil.getUserNameFromToken(token)

        val nickname = memberRepository.findByIdEquals(memberId)?.nickname!!

        return postCommentRepository.getUserPostCommentWithPaging(nickname, pageable)
    }

    override fun addPostComment(token: String, postComment: PostComment): PostComment {
        val memberId = AuthorizationUtil.getUserNameFromToken(token)

        val nickname = memberRepository.findByIdEquals(memberId)?.nickname!!

        if(nickname != postComment.regId)
            throw UnauthorizedException()

        return postCommentRepository.save(postComment)
    }

    override fun updatePostComment(token: String, postComment: PostComment): PostComment {
        val memberId = AuthorizationUtil.getUserNameFromToken(token)

        val nickname = memberRepository.findByIdEquals(memberId)?.nickname!!

        if(nickname != postComment.regId)
            throw UnauthorizedException()

        var updatePostComment = postCommentRepository.findBySeqId(postComment.seqId!!)

        updatePostComment.content = postComment.content
        updatePostComment.updDate = Date()

        return postCommentRepository.save(updatePostComment)
    }

    override fun deletePostComment(token: String, postComment: PostComment) {
        val memberId = AuthorizationUtil.getUserNameFromToken(token)

        val nickname = memberRepository.findByIdEquals(memberId)?.nickname!!

        if(nickname != postComment.regId)
            throw UnauthorizedException()

        var updatePostComment = postCommentRepository.findBySeqId(postComment.seqId!!)

        updatePostComment.deleteFlag = true
        updatePostComment.delDate = Date()

        postCommentRepository.save(updatePostComment)
    }
}