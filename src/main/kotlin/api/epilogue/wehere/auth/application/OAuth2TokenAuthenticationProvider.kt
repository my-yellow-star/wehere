package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberAuthentication
import api.epilogue.wehere.auth.domain.OAuth2Attribute
import api.epilogue.wehere.auth.domain.OAuth2TokenAuthentication
import api.epilogue.wehere.auth.domain.OAuth2TokenAuthorizer
import api.epilogue.wehere.member.application.CreateMemberInput
import api.epilogue.wehere.member.application.CreateMemberUseCase
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class OAuth2TokenAuthenticationProvider(
    private val authorizer: OAuth2TokenAuthorizer,
    private val createMemberUseCase: CreateMemberUseCase
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val attribute = authorizer.authorize(authentication as OAuth2TokenAuthentication)
        val output = createMemberUseCase.execute(toInput(attribute))
        return MemberAuthentication(output.member.id, output.member.grade.toString())
    }

    override fun supports(authentication: Class<*>?): Boolean =
        authentication?.isAssignableFrom(OAuth2TokenAuthentication::class.java) ?: false

    private fun toInput(attribute: OAuth2Attribute): CreateMemberInput =
        CreateMemberInput(
            nickname = null,
            email = attribute.email,
            platformType = attribute.type,
            platformUid = attribute.uid,
            profileImageUrl = attribute.profileImage
        )
}