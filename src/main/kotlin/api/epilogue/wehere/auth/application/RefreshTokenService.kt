package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberPrincipal
import java.util.UUID

interface RefreshTokenService {
    fun create(memberPrincipal: MemberPrincipal): UUID

    fun refresh(token: UUID): UUID
}