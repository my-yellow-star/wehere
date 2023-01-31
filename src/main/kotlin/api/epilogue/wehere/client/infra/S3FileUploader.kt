package api.epilogue.wehere.client.infra

import api.epilogue.wehere.client.application.FileUploader
import api.epilogue.wehere.client.application.UploadResult
import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.web.multipart.MultipartFile

class S3FileUploader(
    private val amazonS3: AmazonS3Client,
    private val bucket: String
) : FileUploader {
    override fun upload(file: MultipartFile, filename: String): UploadResult =
        kotlin.runCatching {
            val metadata = ObjectMetadata()
                .apply {
                    contentLength = file.size
                    contentType = file.contentType
                }
            val inputStream = file.inputStream
            amazonS3.putObject(
                PutObjectRequest(bucket, filename, inputStream, metadata)
                    .withCannedAcl(
                        CannedAccessControlList.PublicRead
                    )
            )
            UploadResult(getUrl(filename))
        }.getOrElse { throw ApiError(ErrorCause.UPLOAD_FAILED) }

    private fun getUrl(fileName: String) =
        amazonS3.getUrl(bucket, fileName).toString()
}