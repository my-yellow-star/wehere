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
        if (isAlreadyExist(input.platformUid, input.platformType))
            return@run CreateMemberStatus.ALREADY_CREATED
        memberRepository.save(input.toMember())
        CreateMemberStatus.SUCCESS
    }

    private fun isAlreadyExist(platformUid: String, platformType: MemberPlatformType) =
        memberRepository
            .findByPlatformUidAndPlatformType(platformUid, platformType) != null
}