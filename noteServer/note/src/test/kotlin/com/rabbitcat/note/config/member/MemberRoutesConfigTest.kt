package com.rabbitcat.note.config.member

import com.rabbitcat.note.domain.member.Member
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import reactor.core.publisher.toMono
import java.time.Duration


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberRoutesConfigTest {

    @Autowired
    lateinit var webTestClient : WebTestClient

    @Before
    fun setUp() {
        webTestClient = webTestClient
                .mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build()
    }

    @Test
    fun memberInfoByIdTest(){

        webTestClient.get().uri("/api/v1/member/jeong")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk
                .expectBody(Member::class.java)
    }

    @Test
    fun memberInfoAll(){

        webTestClient.get().uri("/api/v1/member")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBodyList(Member::class.java)
    }

    @Test
    fun insertMember(){

        var member: Member = Member(id = "jeong1", password = "12345", nickname = "정", address = "광주시", email = "jeong@naver.com", phoneNumber = "01012341234", regDate = null)

        webTestClient.post().uri("/api/v1/member/jeong1")
                .body(member.toMono())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isBadRequest()
                .expectBody()
    }

}