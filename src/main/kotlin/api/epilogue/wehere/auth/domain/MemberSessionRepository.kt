package api.epilogue.wehere.auth.domain

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface MemberSessionRepository : JpaRepository<MemberSession, UUID> {
    fun findByToken(token: UUID): MemberSession?
}