package com.rabbitcat.note.exception

class UnsupportedAuthorizationException: RuntimeException() {
    override val message: String?
        get() = "unsupported authorization method"
}