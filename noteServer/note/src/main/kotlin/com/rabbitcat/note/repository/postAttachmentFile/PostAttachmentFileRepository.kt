package com.rabbitcat.note.repository.postAttachmentFile

import com.rabbitcat.note.domain.postAttachmentFile.PostAttachmentFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostAttachmentFileRepository: JpaRepository<PostAttachmentFile, Number>{
    fun findBySeqIdAndDeleteFlagIs(seqId: Int, deleteFlag: Boolean = false): PostAttachmentFile
}
