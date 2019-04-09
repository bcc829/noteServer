package com.rabbitcat.note.exception

class FileDownloadException(message: String ?= null): RuntimeException(message) {
    override val message: String?
        get() = if(super.message == null) "file download fail" else super.message
}