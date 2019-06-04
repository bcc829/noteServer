package com.rabbitcat.note.controller.postAttachmentFiles

import com.rabbitcat.note.domain.postAttachmentFile.PostAttachmentFile
import com.rabbitcat.note.domain.postAttachmentFile.QPostAttachmentFile.postAttachmentFile
import com.rabbitcat.note.service.postAttachmentFile.PostAttachmentFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URLConnection
import java.security.Principal
import javax.activation.MimetypesFileTypeMap

@RestController
@RequestMapping("api/v1/")
class postAttachmentFileController {

    @Autowired
    lateinit var postAttachmentFilesService: PostAttachmentFileService

    @GetMapping("/postAttachmentFile/{fileSeqId}")
    fun getPostAttachmentFileController(@PathVariable fileSeqId: Int):  ResponseEntity<Any>{
        val postAttachmentFile = postAttachmentFilesService.getPostAttachmentFile(fileSeqId)
        val postAttachmentFileByteArray = postAttachmentFilesService.getPostAttachmentFileByteArray(fileSeqId)

        val byteArrayResource = ByteArrayResource(postAttachmentFileByteArray)
        val fileTypeMap = MimetypesFileTypeMap()

        val mediaType = MediaType.parseMediaType(fileTypeMap.getContentType(postAttachmentFile.realFileName))

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${postAttachmentFile.realFileName}")
                .contentType(mediaType).contentLength(byteArrayResource.contentLength()).body(byteArrayResource)
    }

    @PostMapping("/postAttachmentFile/{postId}")
    fun addPostAttachmentFileController(principal: Principal, @PathVariable postId: Int, @RequestParam(value = "file", required = true) file: MultipartFile): ResponseEntity<Any> {
        return ResponseEntity(postAttachmentFilesService.addPostAttachmentFile(principal.name.toString(), postId, file), HttpStatus.OK)
    }

    @DeleteMapping("/postAttachmentFile/{fileSeqId}")
    fun deletePostAttachmentFileController(principal: Principal, @PathVariable fileSeqId: Int, @RequestParam(value = "file", required = true) file: MultipartFile): ResponseEntity<Any> {
        return ResponseEntity(postAttachmentFilesService.deletePostAttachmentFile(principal.name.toString(), fileSeqId), HttpStatus.OK)
    }

}