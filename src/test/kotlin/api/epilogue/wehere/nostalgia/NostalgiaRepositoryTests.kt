package api.epilogue.wehere.nostalgia

import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.member.domain.Member.MemberPlatformType
import api.epilogue.wehere.member.domain.MemberRepository
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NostalgiaRepositoryTests @Autowired constructor(
    private val nostalgiaRepository: NostalgiaRepository,
    private val memberRepository: MemberRepository
) {
    @BeforeAll
    fun saveMember() {
        val member = Member(
            nickname = "member_123",
            email = "tester@gmail.com",
            platformUid = "28881093020400",
            platformType = MemberPlatformType.GOOGLE
        )
        memberRepository.save(member)
    }

    @Test
    @DisplayName("추억 저장")
    fun save() {
        val latitude = 37.532600
        val longitude = 127.024612
        val member = memberRepository
            .findByPlatformUidAndPlatformType("28881093020400", MemberPlatformType.GOOGLE)!!
        val nostalgia = Nostalgia(
            member = member,
            title = "test nostalgia",
            description = "it is test nostalgia",
            latitude = latitude,
            longitude = longitude,
            visibility = Nostalgia.NostalgiaVisibility.ALL
        )
        val saved = nostalgiaRepository.save(nostalgia)
        Assertions.assertEquals(saved.member.nickname, "member_123")
        Assertions.assertEquals(saved.location.x, latitude)
        Assertions.assertEquals(saved.location.y, longitude)
    }
}