package api.epilogue.wehere.kernel

import javax.persistence.MappedSuperclass
import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents

@MappedSuperclass
abstract class BaseAggregateRoot : BasePersistable() {
    @Transient
    private val domainEvents: MutableList<Any> = mutableListOf()

    protected fun registerEvent(event: Any) {
        domainEvents.add(event)
    }

    @AfterDomainEventPublication
    protected fun clearDomainEvents() {
        domainEvents.clear()
    }

    @DomainEvents
    protected fun domainEvents() =
        domainEvents.toList()
}