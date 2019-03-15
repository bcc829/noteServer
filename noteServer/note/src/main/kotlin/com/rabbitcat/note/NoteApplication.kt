package com.rabbitcat.note

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication

@EnableAspectJAutoProxy
class NoteApplication

fun main(args: Array<String>) {
    runApplication<NoteApplication>(*args)
}
