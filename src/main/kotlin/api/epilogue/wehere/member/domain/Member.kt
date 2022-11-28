package api.epilogue.wehere.member.domain

import api.epilogue.wehere.kernel.BasePersistable
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Member(
    var nickname: String,
    var email: String,
    val platformUid: String,
    val platformType: MemberPlatformType,
    var profileImageUrl: String? = null
) : BasePersistable() {
    @Enumerated(EnumType.STRING)
    var state: MemberState = MemberState.ACTIVE

    @Enumerated(EnumType.STRING)
    var grade: MemberGrade = MemberGrade.FREE_TIER

    enum class MemberState {
        ACTIVE,
        RESIGNED
    }

    enum class MemberPlatformType {
        GOOGLE,
        APPLE,
        KAKAO
    }

    enum class MemberGrade {
        FREE_TIER,
        PRO
    }
}