package com.rabbitcat.note

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder



@RunWith(SpringRunner::class)
@SpringBootTest
class NoteApplicationTests {

	@Test
	fun 비밀번호_암호화() {
		val bcr = BCryptPasswordEncoder()
		val result = bcr.encode("testScecret3")
		println("암호 === $result")
	}

}
