package com.rabbitcat.note.controller.member

import com.rabbitcat.note.domain.member.Member
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

//    @Autowired
//    lateinit var webTestClient : WebTestClient

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @LocalServerPort
    var randomServerPort: Int = 0

    var baseURL =  ""

    @Before
    fun setUp() {
//        webTestClient = webTestClient
//                .mutate()
//                .responseTimeout(Duration.ofMillis(30000))
//                .build()
        baseURL = "http://localhost:$randomServerPort"
    }

    @Test
    fun memberInfoByIdTest(){

//        webTestClient.get().uri("/api/v1/member")
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Basic amVvbmczNDU6MzQ1Njc4")
//                .exchange().expectStatus().isOk
//                .expectBody(Member::class.java)

        var header = HttpHeaders()
        header.add("Authorization", "Basic amVvbmczNDU6MzQ1Njc4")

        val url = baseURL + "/api/v1/member"

        var response: ResponseEntity<Any> = restTemplate.exchange(url, HttpMethod.GET, HttpEntity<Any>(header), Any())

        Assert.assertEquals(200, response.statusCodeValue)
    }

    @Test
    fun memberInfoAll(){

//        webTestClient.get().uri("/api/v1/member/all")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange().expectStatus().isOk
//                .expectBodyList(Member::class.java)


        val url = baseURL + "/api/v1/member/all"

        var response: ResponseEntity<Any> = restTemplate.getForEntity(url, Any::class.java)

        Assert.assertEquals(200, response.statusCodeValue)
    }

    @Test
    fun insertMember(){

        var member: Member = Member(id = "jeong1", password = "12345", nickname = "정", address = "광주시", email = "jeong@naver.com", phoneNumber = "01012341234", regDate = null)

//        webTestClient.post().uri("/api/v1/join")
//                .body(member.toMono())
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange().expectStatus().isBadRequest
//                .expectBody()
    }

}


