package api.epilogue.wehere.member

import api.epilogue.wehere.member.application.CreateMemberInput
import api.epilogue.wehere.member.application.CreateMemberStatus
import api.epilogue.wehere.member.application.CreateMemberUseCase
import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.member.domain.Member.MemberPlatformType
import api.epilogue.wehere.member.domain.MemberRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class MemberServiceSpec : DescribeSpec({
    val memberRepository = mockk<MemberRepository>()

    describe("CreateMemberUseCase") {
        every { memberRepository.save(any()) } returns savedMember
        val createMemberUserCase = CreateMemberUseCase(memberRepository)

        context("새로 가입하는 경우") {
            every {
                memberRepository.findByPlatformUidAndPlatformType(any(), any())
            } returns null

            it("SUCCESS 를 반환한다") {
                val result = createMemberUserCase
                    .execute(createMemberInput())
                result.status shouldBe CreateMemberStatus.SUCCESS
            }
        }

        context("이미 가입한 경우") {
            every {
                memberRepository.findByPlatformUidAndPlatformType(any(), any())
            } returns existMember

            it("ALREADY_CREATED 를 반환한다") {
                val result = createMemberUserCase
                    .execute(createMemberInput())
                result.status shouldBe CreateMemberStatus.ALREADY_CREATED
            }
        }
    }
}) {
    companion object {
        val existMember = Member(
            nickname = "exist_member",
            email = "tester@gmail.com",
            platformUid = "1234567890",
            platformType = MemberPlatformType.GOOGLE
        )
        val savedMember = Member(
            nickname = "saved_member",
            email = "tester@gmail.com",
            platformUid = "1234567890",
            platformType = MemberPlatformType.GOOGLE
        )

        fun createMemberInput(nickname: String? = null) =
            CreateMemberInput(
                nickname = nickname,
                email = "tester@gmail.com",
                platformUid = "1234567890",
                platformType = MemberPlatformType.GOOGLE,
                profileImageUrl = null
            )
    }
}