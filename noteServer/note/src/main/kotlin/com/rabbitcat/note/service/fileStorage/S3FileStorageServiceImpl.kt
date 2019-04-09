package com.rabbitcat.note.service.fileStorage

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.*


@Service
class S3FileStorageServiceImpl: FileStorageService {

    @Autowired
    lateinit var amazonS3: AmazonS3

    override fun downloadFile(bucketName: String, subDirectory: String, fileName: String): ByteArray {


        var bufferedInputStream = amazonS3.getObject(bucketName, "$subDirectory/$fileName").objectContent.buffered()
        var byteArrayOutputStream = ByteArrayOutputStream()

        val outputByte = ByteArray(4096)

        while (bufferedInputStream.read(outputByte, 0, 4096) != -1){
            byteArrayOutputStream.write(outputByte, 0, 4096)
        }

        bufferedInputStream.close()
        byteArrayOutputStream.flush()
        byteArrayOutputStream.close()

        return byteArrayOutputStream.toByteArray()
    }


    override fun uploadFile(bucketName: String, subDirectory: String, fileName: String, fileData: ByteArray): String {
        val filePath = "$subDirectory/$fileName"

        val metaData = ObjectMetadata()

        metaData.contentLength = fileData.size.toLong()
        val byteArrayInputStream = ByteArrayInputStream(fileData)

        val putObjectRequest = PutObjectRequest(bucketName, filePath, byteArrayInputStream, metaData)
        putObjectRequest.cannedAcl = CannedAccessControlList.PublicRead

        amazonS3.putObject(putObjectRequest)

        return amazonS3.getUrl(bucketName, fileName).toString()
    }

    override fun deleteFile(bucketName: String, subDirectory: String, fileName: String) {
        amazonS3.deleteObject(bucketName, "$subDirectory/$fileName")
    }
}