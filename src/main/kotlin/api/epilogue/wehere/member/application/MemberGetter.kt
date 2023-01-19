package api.epilogue.wehere.member.application

import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.member.domain.MemberRepository
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberGetter(
    private val repository: MemberRepository
) {
    fun get(memberId: UUID) =
        repository.findByIdOrNull(memberId)
            ?.let { MemberOutput.of(it) }
            ?: throw ApiError(ErrorCause.ENTITY_NOT_FOUND)
}