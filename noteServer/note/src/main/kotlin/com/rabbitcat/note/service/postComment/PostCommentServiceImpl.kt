package com.rabbitcat.note.service.postComment

import com.rabbitcat.note.common.constant.Constant
import com.rabbitcat.note.common.util.AuthorizationUtil
import com.rabbitcat.note.domain.postComment.PostComment
import com.rabbitcat.note.exception.UnauthorizedException
import com.rabbitcat.note.repository.member.MemberRepository
import com.rabbitcat.note.repository.postComment.PostCommentRepository
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class PostCommentServiceImpl: PostCommentService {

    @Autowired
    lateinit var postCommentRepository: PostCommentRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun getPostCommentWithPaging(postSeqId: Int, pageable: Pageable): Page<PostComment> {
        return postCommentRepository.getPostCommentWithPaging(postSeqId, pageable)
    }

    override fun getUserPostCommentWithPaging(id: String, pageable: Pageable): Page<PostComment> {

        val nickname = memberRepository.findByIdEquals(id)!!.nickname

        return postCommentRepository.getUserPostCommentWithPaging(nickname, pageable)
    }

    override fun addPostComment(id: String, postComment: PostComment): PostComment {

        val nickname = memberRepository.findByIdEquals(id)?.nickname

        if(nickname != postComment.regId)
            throw UnauthorizedException()

        return postCommentRepository.save(postComment)
    }

    override fun updatePostComment(id: String, postComment: PostComment): PostComment {

        val nickname = memberRepository.findByIdEquals(id)!!.nickname

        if(nickname != postComment.regId)
            throw UnauthorizedException()

        var updatePostComment = postCommentRepository.findBySeqId(postComment.seqId!!)

        updatePostComment.content = postComment.content
        updatePostComment.updDate = DateTime.now(DateTimeZone.forID(Constant.TIME_ZONE)).toDate()

        return postCommentRepository.save(updatePostComment)
    }

    override fun deletePostComment(id: String, postComment: PostComment) {
        val nickname = memberRepository.findByIdEquals(id)?.nickname

        if(nickname != postComment.regId)
            throw UnauthorizedException()

        var updatePostComment = postCommentRepository.findBySeqId(postComment.seqId!!)

        updatePostComment.deleteFlag = true
        updatePostComment.delDate = DateTime.now(DateTimeZone.forID(Constant.TIME_ZONE)).toDate()

        postCommentRepository.save(updatePostComment)
    }
}