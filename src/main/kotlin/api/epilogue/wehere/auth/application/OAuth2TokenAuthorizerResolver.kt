package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.OAuth2TokenAuthentication
import api.epilogue.wehere.auth.domain.OAuth2TokenAuthorizer
import api.epilogue.wehere.auth.domain.OAuth2TokenAuthorizer.PlatformSpecificOAuth2TokenAuthorizer
import org.springframework.stereotype.Component

@Component
class OAuth2TokenAuthorizerResolver(
    private val authorizers: List<PlatformSpecificOAuth2TokenAuthorizer>
) : OAuth2TokenAuthorizer {
    override fun authorize(authentication: OAuth2TokenAuthentication) =
        authorizers
            .single { it.provider == authentication.provider }
            .authorizeInternal(authentication)
}