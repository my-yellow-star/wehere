package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.auth.domain.MemberSession
import api.epilogue.wehere.auth.domain.MemberSessionRepository
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberSessionService(
    private val memberSessionRepository: MemberSessionRepository
) : RefreshTokenService {
    @Transactional
    override fun create(memberPrincipal: MemberPrincipal) =
        memberSessionRepository.save(toMemberSession(memberPrincipal)).token

    override fun refresh(token: UUID): UUID {
        TODO("Not yet implemented")
    }

    private fun toMemberSession(memberPrincipal: MemberPrincipal) =
        MemberSession(
            memberPrincipal.id,
            UUID.randomUUID()
        )
}