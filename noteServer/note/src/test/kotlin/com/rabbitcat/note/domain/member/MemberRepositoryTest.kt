package com.rabbitcat.note.domain.member

import com.rabbitcat.note.repository.member.MemberRepository
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
   lateinit var memberRepository : MemberRepository

    @Before
    fun cleanUp(){
        var member = memberRepository.findByIdEquals("jeong345")

        if(member != null)
            memberRepository.delete(member)
    }

    @Test
    fun insert_member_data(){

        val id  = "jeong345"
        val password = "3456"
        val address = "인천시"
        val phoneNumber = "01000001111"
        val email = "test@email.com"
        val nickName = "정1"

        var member = Member(id = id, password = password, address = address, phoneNumber = phoneNumber, email = email, nickname = nickName, regDate = null)

        memberRepository.save(member)

        var searchMember = memberRepository.findByIdEquals(id)

        assertEquals("jeong3", searchMember?.id)

    }

    @Test
    fun select_member_data_by_user_id(){
        var member = memberRepository.findByIdEquals(id = "jeong345")
        println(member.toString());

        assertEquals("test@email.com", member?.email)
    }

    @After
    fun cleanMember(){
        var member = memberRepository.findByIdEquals("jeong345")

        if(member != null)
            memberRepository.delete(member)
    }
}