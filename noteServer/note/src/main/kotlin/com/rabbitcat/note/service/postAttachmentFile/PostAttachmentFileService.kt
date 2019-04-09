package com.rabbitcat.note.service.postAttachmentFile

import com.rabbitcat.note.domain.postAttachmentFile.PostAttachmentFile
import org.springframework.web.multipart.MultipartFile

interface PostAttachmentFileService {
    fun getPostAttachmentFile(fileSeqId: Int): PostAttachmentFile
    fun getPostAttachmentFileByteArray(fileSeqId: Int): ByteArray
    fun addPostAttachmentFile(token: String, postId: Int, file: MultipartFile): PostAttachmentFile
    fun deletePostAttachmentFile(token: String, fileSeqId: Int)
}