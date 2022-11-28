package api.epilogue.wehere.member.domain

import api.epilogue.wehere.kernel.BaseRepository
import api.epilogue.wehere.member.domain.Member.MemberPlatformType

interface MemberRepository : BaseRepository<Member> {
    fun findByPlatformUidAndPlatformType(platformUid: String, platformType: MemberPlatformType): Member?
}