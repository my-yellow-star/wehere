package api.epilogue.wehere.nostalgia

import api.epilogue.wehere.kernel.LocationUtils
import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.member.domain.Member.MemberPlatformType
import api.epilogue.wehere.member.domain.MemberRepository
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaSpec
import javax.persistence.EntityManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
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

    @BeforeEach
    fun clear() {
        nostalgiaRepository.deleteAll()
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
    @DisplayName("반경 N km 이내 추억 조회")
    fun selectAllInRadius() {
        val current = LocationUtils.toPoint(37.514575, 127.0495556) // 서울 강남구
        val distanceInKilometer = 20
        val member = memberRepository.findAll().first()
        val dummyList = listOf(
            createDummyNostalgia(member, 37.52736667, 127.1258639), // 서울 강동구
            createDummyNostalgia(member, 37.53573889, 127.0845333), // 서울 광진구
            createDummyNostalgia(member, 37.65146111, 127.0583889), // 서울 노원구
            createDummyNostalgia(member, 36.3506295, 127.3848616), // 대전
            createDummyNostalgia(member, 37.47384843, 126.6217617) // 인천 중구
        )
        val spec = NostalgiaSpec.filterVisible(member.id)
            .and(NostalgiaSpec.distanceLessThan(current, distanceInKilometer * 1000.0))
        nostalgiaRepository.saveAll(dummyList)
        val resultIds = nostalgiaRepository
            .findAll(spec)
            .map(Nostalgia::id)
        Assertions.assertTrue(resultIds.contains(dummyList[0].id))
        Assertions.assertTrue(resultIds.contains(dummyList[1].id))
        Assertions.assertTrue(resultIds.contains(dummyList[2].id))
        Assertions.assertTrue(resultIds.contains(dummyList[3].id).not())
        Assertions.assertTrue(resultIds.contains(dummyList[4].id).not())
    }

    @Test
    @DisplayName("추억 조회 시 거리순 정렬")
    fun selectOrderByDistance() {
        val current = LocationUtils.toPoint(2.5, 2.5)
        val member = memberRepository.findAll().first()
        val dummyList = listOf(
            createDummyNostalgia(member, 1.0, 1.0),
            createDummyNostalgia(member, 1.0, 2.0),
            createDummyNostalgia(member, 2.0, 3.0),
            createDummyNostalgia(member, 3.0, 4.0),
            createDummyNostalgia(member, 5.0, 6.0)
        )
        nostalgiaRepository.saveAll(dummyList)
        val spec = NostalgiaSpec.filterVisible(member.id)
            .and(NostalgiaSpec.orderByDistance(current))
        val result =
            nostalgiaRepository.findAll(spec)
        Assertions.assertEquals(result.first().id, dummyList[2].id)
        Assertions.assertEquals(result[1].id, dummyList[3].id)
        Assertions.assertEquals(result[2].id, dummyList[1].id)
        Assertions.assertEquals(result[3].id, dummyList[0].id)
        Assertions.assertEquals(result[4].id, dummyList[4].id)
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