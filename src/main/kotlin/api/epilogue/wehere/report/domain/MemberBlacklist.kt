package api.epilogue.wehere.report.domain

import api.epilogue.wehere.kernel.BasePersistable
import api.epilogue.wehere.member.domain.Member
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class MemberBlacklist(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    val member: Member,
    val targetId: UUID
) : BasePersistable()