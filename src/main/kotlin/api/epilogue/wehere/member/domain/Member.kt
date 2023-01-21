package api.epilogue.wehere.member.domain

import api.epilogue.wehere.kernel.BasePersistable
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import org.hibernate.annotations.Where

@Entity
@Where(clause = "state != 'RESIGNED'")
class Member(
    var nickname: String,
    var email: String,
    val platformUid: String,
    @Enumerated(EnumType.STRING)
    val platformType: MemberPlatformType,
    var profileImageUrl: String? = null
) : BasePersistable() {
    @Enumerated(EnumType.STRING)
    var state: MemberState = MemberState.ACTIVE

    @Enumerated(EnumType.STRING)
    var grade: MemberGrade = MemberGrade.FREE_TIER

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
        KAKAO
    }

    enum class MemberGrade {
        FREE_TIER,
        PRO
    }
}