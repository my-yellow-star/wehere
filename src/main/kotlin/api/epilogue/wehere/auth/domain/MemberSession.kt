package api.epilogue.wehere.auth.domain

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import org.hibernate.annotations.Where

@Entity
@Where(clause = "expiredAt < NOW()")
class MemberSession(
    val memberId: UUID,
    val token: UUID,
    val expiredAt: Instant,
    val createdAt: Instant,
    @Id
    val id: UUID = UUID.randomUUID()
) {
    companion object {
        fun of(oAuth2Member: OAuth2Member) =
            MemberSession(
                oAuth2Member.id,
                UUID.randomUUID(),
                Instant.now().plus(30, ChronoUnit.DAYS),
                Instant.now()
            )
    }
}