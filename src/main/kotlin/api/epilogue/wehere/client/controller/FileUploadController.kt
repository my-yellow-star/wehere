package api.epilogue.wehere.client.controller

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.client.application.FileUploader
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/files")
class FileUploadController(
    private val uploader: FileUploader
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun post(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestPart("file") file: MultipartFile
    ) =
        uploader.upload(principal.id, file)
}
