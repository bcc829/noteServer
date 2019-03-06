package com.rabbitcat.note.domain.member

import com.rabbitcat.note.repository.member.MemberRepository
import junit.framework.TestCase.assertEquals
import org.junit.After
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

    @After
    fun cleanUp(){

    }

    @Test
    fun select_member_data_by_seq_id(){
       var member = memberRepository.findBySeqIdEquals(3)
       println(member.toString())

        assertEquals("jeong", member?.id)
    }

    @Test
    fun select_member_data_by_user_id(){
        var member = memberRepository.findByIdEquals(id = "jeong")
        println(member.toString());

        assertEquals("jeong@email.com", member.email)
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

        assertEquals("jeong3", searchMember.id)

    }
}