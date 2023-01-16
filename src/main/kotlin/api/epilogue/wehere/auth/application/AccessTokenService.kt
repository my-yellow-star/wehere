package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.OAuth2Member

interface AccessTokenService {
    fun create(oAuth2Member: OAuth2Member): String
}