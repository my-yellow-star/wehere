package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberPrincipal

interface AccessTokenService {
    fun create(memberPrincipal: MemberPrincipal): String
}