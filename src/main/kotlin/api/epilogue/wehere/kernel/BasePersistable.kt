package api.epilogue.wehere.kernel

import java.time.Instant
import java.util.UUID
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Version
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BasePersistable {
    @Id
    @Column(columnDefinition = "binary(16)")
    val id: UUID = UUID.randomUUID()

    @Version
    var version: Int = 0

    @CreatedDate
    var createdAt: Instant = Instant.MIN
        protected set

    @LastModifiedDate
    var updatedAt: Instant = Instant.MIN
        protected set
}