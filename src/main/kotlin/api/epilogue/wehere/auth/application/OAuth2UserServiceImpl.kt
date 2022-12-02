package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.OAuth2Attribute
import api.epilogue.wehere.auth.domain.OAuth2Member
import api.epilogue.wehere.member.application.CreateMemberInput
import api.epilogue.wehere.member.application.CreateMemberUseCase
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserServiceImpl(
    private val createMemberUseCase: CreateMemberUseCase
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = DefaultOAuth2UserService().loadUser(userRequest)
        val attribute = OAuth2Attribute.of(
            userRequest.clientRegistration.registrationId,
            oAuth2User.attributes
        )
        val output = createMemberUseCase.execute(toInput(attribute))
        return OAuth2Member.of(output.member)
    }

    private fun toInput(attribute: OAuth2Attribute): CreateMemberInput =
        CreateMemberInput(
            nickname = null,
            email = attribute.email,
            platformType = attribute.type,
            platformUid = attribute.uid,
            profileImageUrl = attribute.profileImage
        )
}