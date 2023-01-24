package api.epilogue.wehere.auth.domain

interface OAuth2TokenAuthorizer {
    fun authorize(authentication: OAuth2TokenAuthentication): OAuth2Attribute

    interface PlatformSpecificOAuth2TokenAuthorizer {
        val provider: String
        fun authorizeInternal(authentication: OAuth2TokenAuthentication): OAuth2Attribute
    }
}