package api.epilogue.wehere.member.application

import api.epilogue.wehere.member.domain.Member.MemberPlatformType
import api.epilogue.wehere.member.domain.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateMemberUseCase(
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun execute(input: CreateMemberInput) = run {
        getMemberIfExists(input.platformUid, input.platformType)
            ?.let {
                return@run CreateMemberOutput.alreadyCreated(it)
            }

        CreateMemberOutput.success(memberRepository.save(input.toMember()))
    }

    private fun getMemberIfExists(platformUid: String, platformType: MemberPlatformType) =
        memberRepository
            .findByPlatformUidAndPlatformType(platformUid, platformType)
}