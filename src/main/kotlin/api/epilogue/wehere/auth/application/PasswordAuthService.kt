package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.member.application.CreateMemberInput
import api.epilogue.wehere.member.application.CreateMemberUseCase
import api.epilogue.wehere.member.domain.Member.MemberPlatformType
import api.epilogue.wehere.member.domain.MemberRepository
import java.util.UUID
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordAuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val createMemberUseCase: CreateMemberUseCase,
    private val credentialPublisher: CredentialPublisher
) {
    fun register(email: String, password: String) {
        if (memberRepository.findByEmail(email) != null)
            throw ApiError(ErrorCause.AUTH_FAILED)

        createMemberUseCase.execute(
            CreateMemberInput(
                email = email,
                password = passwordEncoder.encode(password),
                platformType = MemberPlatformType.BASIC,
                platformUid = "Basic ${UUID.randomUUID()}",
                nickname = null,
                profileImageUrl = null
            )
        )
    }

    fun login(email: String, password: String): TokenCredential {
        val member = memberRepository.findByEmail(email)
            ?.takeIf { passwordEncoder.matches(password, it.password) }
            ?: throw ApiError(ErrorCause.AUTH_FAILED)
        return credentialPublisher.publishToken(MemberPrincipal(member.id, member.grade))
    }
}