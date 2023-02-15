package api.epilogue.wehere.member.application

import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.member.domain.MemberRepository
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberGetter(
    private val repository: MemberRepository
) {
    fun get(memberId: UUID) =
        repository.findByIdOrNull(memberId)
            ?.let { MemberOutput.of(it) }
            ?: throw ApiError(ErrorCause.ENTITY_NOT_FOUND)

    @Transactional(readOnly = true)
    fun getOther(principalId: UUID, memberId: UUID): MemberOutput {
        val principal = repository.getReferenceById(principalId)
        val member = repository.findByIdOrNull(memberId)
            ?: throw ApiError(ErrorCause.ENTITY_NOT_FOUND)
        return MemberOutput.ofOther(principal, member)
    }
}