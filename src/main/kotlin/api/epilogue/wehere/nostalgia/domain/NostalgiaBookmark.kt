package api.epilogue.wehere.nostalgia.domain

import api.epilogue.wehere.kernel.BasePersistable
import api.epilogue.wehere.member.domain.Member
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import org.hibernate.annotations.Where

@Entity
@Where(clause = "state != 'INACTIVE'")
class NostalgiaBookmark(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    val member: Member,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nostalgiaId")
    val nostalgia: Nostalgia
) : BasePersistable() {
    @Enumerated(EnumType.STRING)
    var state: BookmarkState = BookmarkState.ACTIVE
        protected set

    enum class BookmarkState {
        ACTIVE,
        INACTIVE
    }

    fun delete() {
        state = BookmarkState.INACTIVE
    }
}