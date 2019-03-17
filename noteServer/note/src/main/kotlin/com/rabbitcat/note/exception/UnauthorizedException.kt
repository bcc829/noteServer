package com.rabbitcat.note.exception

import java.lang.RuntimeException

class UnauthorizedException: RuntimeException(){
    override val message: String?
        get() = "Unauthorized"
}