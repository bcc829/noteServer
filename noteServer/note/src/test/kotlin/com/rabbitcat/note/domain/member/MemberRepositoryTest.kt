package com.rabbitcat.note.domain.member

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
    public fun cleanUp(){

    }

    @Test
    public fun select_member_data_by_seq_id(){
       var member = memberRepository.findBySeqIdEquals(2)
       println(member.toString())

        assertEquals("jeong", member.id)
    }

    @Test fun select_member_data_by_user_id(){
        var member = memberRepository.findByIdEquals(id = "jeong")
        println(member.toString());

        assertEquals("jeong@email.com", member.email)
    }

}