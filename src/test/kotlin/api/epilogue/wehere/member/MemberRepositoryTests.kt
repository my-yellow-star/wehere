package api.epilogue.wehere.member

import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.member.domain.MemberRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class MemberRepositoryTests @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    @Test
    @DisplayName("멤버 저장")
    fun save() {
        val member = Member("member_123")
        val saved = memberRepository.save(member)
        Assertions.assertEquals(saved.state, Member.MemberState.ACTIVE)
        Assertions.assertEquals(saved.grade, Member.MemberGrade.FREE_TIER)
    }
}