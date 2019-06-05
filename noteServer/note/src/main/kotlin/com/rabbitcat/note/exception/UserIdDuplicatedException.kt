package com.rabbitcat.note.exception

import java.lang.RuntimeException

class UserIdDuplicatedException: RuntimeException(){
    override val message: String?
        get() = "user info is duplicated"
}