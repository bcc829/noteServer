package com.rabbitcat.note.service.postAttachmentFile

import com.rabbitcat.note.common.util.AuthorizationUtil
import com.rabbitcat.note.domain.postAttachmentFile.PostAttachmentFile
import com.rabbitcat.note.exception.UnauthorizedException
import com.rabbitcat.note.repository.member.MemberRepository
import com.rabbitcat.note.repository.postAttachmentFile.PostAttachmentFileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import com.rabbitcat.note.common.constant.Constant
import com.rabbitcat.note.controller.postAttachmentFiles.postAttachmentFileController
import com.rabbitcat.note.exception.FileDeleteException
import com.rabbitcat.note.exception.FileDownloadException
import com.rabbitcat.note.exception.FileUploadException
import com.rabbitcat.note.repository.post.PostRepository
import com.rabbitcat.note.service.fileStorage.FileStorageService
import org.apache.commons.io.FilenameUtils
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.springframework.beans.factory.annotation.Value
import java.util.*
import javax.transaction.Transactional


@Service
class PostAttachmentFileServicImpl: PostAttachmentFileService {

    @Autowired
    lateinit var postAttachmentFilesRepository: PostAttachmentFileRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var postRepository: PostRepository

    @Value("\${cloud.aws.s3.bucket}")
    lateinit var rootDirectory: String

    @Autowired
    lateinit var fileStorageService: FileStorageService

    override fun getPostAttachmentFile(fileSeqId: Int): PostAttachmentFile {

        try {
            return postAttachmentFilesRepository.findBySeqIdAndDeleteFlagIs(fileSeqId)
        } catch (e: Exception){
            throw FileDownloadException(e.message)
        }

    }

    override fun getPostAttachmentFileByteArray(fileSeqId: Int): ByteArray {

        var byteArray: ByteArray?

        try {
            val postAttachmentFile = postAttachmentFilesRepository.findBySeqIdAndDeleteFlagIs(fileSeqId)
            byteArray = fileStorageService.downloadFile(postAttachmentFile.rootDirectory, postAttachmentFile.subDirectory, postAttachmentFile.extFileName)
        } catch (e: Exception){
            throw FileDownloadException(e.message)
        }

        return byteArray
    }

    @Transactional
    override fun addPostAttachmentFile(id: String, postId: Int, file:MultipartFile): PostAttachmentFile {

        val member = memberRepository.findByIdEquals(id)

        val post = postRepository.findBySeqId(postId)

        if(member == null || member.nickname != post.regId) throw UnauthorizedException()

        val currentTime = DateTime(DateTimeZone.forID(Constant.TIME_ZONE))

        val fileRealName = file.originalFilename!!
        val fileExtName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.originalFilename)
        val subDirectory = currentTime.toString("yyyyMMdd") + "/" + currentTime.toString("HH")

        var filePath: String?

        try{
            filePath = fileStorageService.uploadFile(rootDirectory, subDirectory, fileExtName, file.inputStream.readBytes())
            val postAttachmentFile = PostAttachmentFile(regId = member.nickname, postSeqId = postId, realFileName = fileRealName,
                    extFileName = fileExtName, rootDirectory = rootDirectory, filePath = filePath, subDirectory = subDirectory)

            return postAttachmentFilesRepository.save(postAttachmentFile)
        } catch (e: Exception){
            throw FileUploadException(e.message)
        }

    }

    @Transactional
    override fun deletePostAttachmentFile(id: String, fileSeqId: Int) {

        val member = memberRepository.findByIdEquals(id)
        var postAttachmentFile :PostAttachmentFile

        try {
            postAttachmentFile = postAttachmentFilesRepository.findBySeqIdAndDeleteFlagIs(fileSeqId)
        } catch (e: Exception){
            throw FileDeleteException(e.message)
        }

        if(member == null || member.nickname != postAttachmentFile.regId){
            throw UnauthorizedException()
        }

        try{
           fileStorageService.deleteFile(rootDirectory, postAttachmentFile.subDirectory, postAttachmentFile.extFileName)
           postAttachmentFilesRepository.delete(postAttachmentFile)
        } catch (e: Exception){
            throw FileDeleteException(e.message)
        }

    }
}