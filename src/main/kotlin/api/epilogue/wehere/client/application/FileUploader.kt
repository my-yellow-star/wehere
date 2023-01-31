package api.epilogue.wehere.client.application

import java.util.UUID
import org.springframework.web.multipart.MultipartFile

interface FileUploader {
    fun upload(memberId: UUID, file: MultipartFile): UploadResult
}