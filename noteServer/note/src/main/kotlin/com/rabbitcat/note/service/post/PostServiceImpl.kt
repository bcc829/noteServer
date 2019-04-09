package com.rabbitcat.note.service.post

import com.rabbitcat.note.common.constant.Constant
import com.rabbitcat.note.common.enum.PostSearchType
import com.rabbitcat.note.common.util.AuthorizationUtil
import com.rabbitcat.note.domain.post.Post
import com.rabbitcat.note.exception.UnauthorizedException
import com.rabbitcat.note.repository.member.MemberRepository
import com.rabbitcat.note.repository.post.PostRepository
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostServiceImpl: PostService {

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun getUserNewlyPostLimitOne(token: String): Post? {
        val regId = AuthorizationUtil.getUserIdFromToken(token)

        return postRepository.getPostByUserIdLimitOneOrderByRegDateDesc(regId)
    }

    override fun getPostWithPageableAndSearchType(type: PostSearchType, value: String, pageable: Pageable): Page<Post> {
        return postRepository.getPagingPostWithSearch(type, value, pageable)
    }

    override fun addPost(token: String, post: Post): Post {
        val regId = AuthorizationUtil.getUserIdFromToken(token)

        val member = memberRepository.findByIdEquals(regId)

        if(member?.nickname != post.regId)
            throw UnauthorizedException()

        return postRepository.save(post)
    }

    override fun updatePost(token: String, post: Post): Post {
        val regId = AuthorizationUtil.getUserIdFromToken(token)

        val member = memberRepository.findByIdEquals(regId)

        if(member?.nickname != post.regId)
            throw UnauthorizedException()

        var updatePost = postRepository.findBySeqId(post.seqId!!)

        updatePost.title = post.title
        updatePost.content = post.content
        updatePost.updDate = DateTime.now(DateTimeZone.forID(Constant.TIME_ZONE)).toDate()

        return postRepository.save(updatePost)
    }

    override fun getPost(seqId: Int): Post {
        var post = postRepository.findBySeqId(seqId)

        post.readCount = post.readCount?.plus(1)

        return postRepository.save(post)
    }

    override fun deletePost(token: String, seqId: Int) {
        val regId = AuthorizationUtil.getUserIdFromToken(token)

        var post = postRepository.findBySeqId(seqId)
        
        val member = memberRepository.findByIdEquals(regId)

        if(member?.nickname != post.regId)
            throw UnauthorizedException()

        post.deleteFlag = true

        postRepository.save(post)
    }
}