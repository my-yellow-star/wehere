package api.epilogue.wehere.nostalgia.domain

import java.time.Instant
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class NostalgiaViewLog(
    val memberId: UUID,
    val nostalgiaId: UUID,
    @Id
    val id: UUID = UUID.randomUUID(),
    val createdAt: Instant = Instant.now()
)