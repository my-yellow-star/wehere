package api.epilogue.wehere.nostalgia

import api.epilogue.wehere.kernel.LocationUtils
import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.member.domain.Member.MemberPlatformType
import api.epilogue.wehere.member.domain.MemberRepository
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import javax.persistence.EntityManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NostalgiaRepositoryTests @Autowired constructor(
    private val nostalgiaRepository: NostalgiaRepository,
    private val memberRepository: MemberRepository,
    private val entityManager: EntityManager
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

    @Test
    @DisplayName("특정 반경 이내 추억 조회")
    fun selectAllInRadius() {
        val baseLatitude = 0.0
        val baseLongitude = 0.0
        val radius = 5.0
        val member = memberRepository.findAll().first()
        val dummyList = listOf(
            createDummyNostalgia(member, 1.0, 1.0),
            createDummyNostalgia(member, 1.0, 2.0),
            createDummyNostalgia(member, 2.0, 3.0),
            createDummyNostalgia(member, 3.0, 4.0),
            createDummyNostalgia(member, 5.0, 6.0)
        )
        nostalgiaRepository.saveAll(dummyList)
        val query = entityManager.createQuery(
            "select n from Nostalgia n where within (n.location, :circle) = true",
            Nostalgia::class.java
        )
        query.setParameter(
            "circle", LocationUtils.createCircle(baseLatitude, baseLongitude, radius)
        )
        val resultIds = query.resultList.map(Nostalgia::id)
        Assertions.assertTrue(resultIds.contains(dummyList[0].id))
        Assertions.assertTrue(resultIds.contains(dummyList[1].id))
        Assertions.assertTrue(resultIds.contains(dummyList[2].id))
        Assertions.assertTrue(resultIds.contains(dummyList[3].id).not())
        Assertions.assertTrue(resultIds.contains(dummyList[4].id).not())
    }

    private fun createDummyNostalgia(member: Member, latitude: Double, longitude: Double) =
        Nostalgia(
            member = member,
            title = "dummy nostalgia",
            description = "latitude: $latitude\nlongitude: $longitude",
            latitude = latitude,
            longitude = longitude,
            visibility = Nostalgia.NostalgiaVisibility.ALL
        )
}