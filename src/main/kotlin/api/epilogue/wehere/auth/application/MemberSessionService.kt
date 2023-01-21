package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.auth.domain.MemberSession
import api.epilogue.wehere.auth.domain.MemberSessionRepository
import api.epilogue.wehere.auth.exception.InvalidSessionException
import api.epilogue.wehere.member.domain.MemberRepository
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberSessionService(
    private val memberSessionRepository: MemberSessionRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun create(memberPrincipal: MemberPrincipal) =
        memberSessionRepository.save(toMemberSession(memberPrincipal)).id

    fun findUser(sessionId: UUID) =
        memberSessionRepository.findByIdOrNull(sessionId)
            ?.let {
                memberRepository.findByIdOrNull(it.memberId)
            }
            ?: throw InvalidSessionException()

    @Transactional
    fun expireAll(memberId: UUID) {
        memberSessionRepository.deleteByMemberId(memberId)
    }

    private fun toMemberSession(memberPrincipal: MemberPrincipal) =
        MemberSession(memberPrincipal.id)
}