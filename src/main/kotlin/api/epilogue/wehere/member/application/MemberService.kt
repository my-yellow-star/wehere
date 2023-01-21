package api.epilogue.wehere.member.application

import api.epilogue.wehere.auth.application.MemberSessionService
import api.epilogue.wehere.member.domain.MemberRepository
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val repository: MemberRepository,
    private val sessionService: MemberSessionService
) {
    @Transactional
    fun update(memberId: UUID, input: UpdateMemberInput) {
        val member = repository.getReferenceById(memberId)
        member.apply {
            nickname = input.nickname ?: nickname
            profileImageUrl = input.profileImageUrl ?: profileImageUrl
        }
    }

    @Transactional
    fun delete(memberId: UUID) {
        val member = repository.getReferenceById(memberId)
        member.resign()
        sessionService.expireAll(memberId)
    }
}