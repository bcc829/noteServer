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
import javax.transaction.Transactional

@Service
@Transactional
class PostServiceImpl: PostService {

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun getUserNewlyPostLimitOne(id: String): Post? {

        return postRepository.getPostByUserIdLimitOneOrderByRegDateDesc(id)
    }

    override fun getPostWithPageableAndSearchType(type: PostSearchType, value: String, pageable: Pageable): Page<Post> {
        return postRepository.getPagingPostWithSearch(type, value, pageable)
    }

    override fun addPost(id: String, post: Post): Post {

        val member = memberRepository.findByIdEquals(id)

        if(member?.nickname != post.regId)
            throw UnauthorizedException()

        return postRepository.save(post)
    }

    override fun updatePost(id: String, post: Post): Post {

        val member = memberRepository.findByIdEquals(id)

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

    override fun deletePost(id: String, seqId: Int) {

        var post = postRepository.findBySeqId(seqId)
        
        val member = memberRepository.findByIdEquals(id)

        if(member?.nickname != post.regId)
            throw UnauthorizedException()

        post.deleteFlag = true

        postRepository.save(post)
    }
}