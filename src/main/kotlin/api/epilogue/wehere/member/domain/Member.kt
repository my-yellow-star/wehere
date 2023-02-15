package api.epilogue.wehere.member.domain

import api.epilogue.wehere.kernel.BasePersistable
import api.epilogue.wehere.report.domain.MemberBlacklist
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.OneToMany
import org.hibernate.annotations.Where

@Entity
@Where(clause = "state != 'RESIGNED'")
class Member(
    var nickname: String,
    var email: String,
    val platformUid: String,
    @Enumerated(EnumType.STRING)
    val platformType: MemberPlatformType,
    var profileImageUrl: String? = null,
    var backgroundImageUrl: String? = null,
    var description: String? = null,
    var password: String? = null
) : BasePersistable() {
    @Enumerated(EnumType.STRING)
    var state: MemberState = MemberState.ACTIVE

    @Enumerated(EnumType.STRING)
    var grade: MemberGrade = MemberGrade.FREE_TIER

    @OneToMany(mappedBy = "member")
    val blacklists: List<MemberBlacklist> = listOf()

    fun resign() {
        nickname = "탈퇴한 사용자"
        state = MemberState.RESIGNED
    }

    enum class MemberState {
        ACTIVE,
        RESIGNED
    }

    enum class MemberPlatformType {
        GOOGLE,
        APPLE,
        KAKAO,
        BASIC
    }

    enum class MemberGrade {
        FREE_TIER,
        PRO
    }
}