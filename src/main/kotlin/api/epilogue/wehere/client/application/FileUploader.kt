package api.epilogue.wehere.client.application

import org.springframework.web.multipart.MultipartFile

interface FileUploader {
    fun upload(file: MultipartFile, filename: String): UploadResult
}