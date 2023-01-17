package api.epilogue.wehere.auth.domain

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import org.hibernate.annotations.Where

@Entity
@Where(clause = "expiredAt > NOW()")
class MemberSession(
    val memberId: UUID,
    val expiredAt: Instant = Instant.now().plus(30, ChronoUnit.DAYS),
    val createdAt: Instant = Instant.now(),
    @Id
    val id: UUID = UUID.randomUUID()
)