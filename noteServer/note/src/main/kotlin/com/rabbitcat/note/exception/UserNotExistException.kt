package com.rabbitcat.note.exception

class UserNotExistException: RuntimeException() {
    override val message: String?
        get() = "user is not exist"
}