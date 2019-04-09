package com.rabbitcat.note.service.fileStorage

import java.io.File

interface FileStorageService {
    fun downloadFile(rootDirectory: String, subDirectory: String, fileName: String): ByteArray
    fun uploadFile(rootDirectory: String, subDirectory: String, fileName: String, fileData: ByteArray): String
    fun deleteFile(rootDirectory: String, subDirectory: String, fileName: String)
}