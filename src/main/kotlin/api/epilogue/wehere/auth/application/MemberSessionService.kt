package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberSession
import api.epilogue.wehere.auth.domain.MemberSessionRepository
import api.epilogue.wehere.auth.domain.OAuth2Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberSessionService(
    private val memberSessionRepository: MemberSessionRepository
) : RefreshTokenService {
    @Transactional
    override fun create(oAuth2Member: OAuth2Member) =
        memberSessionRepository.save(MemberSession.of(oAuth2Member)).token
}