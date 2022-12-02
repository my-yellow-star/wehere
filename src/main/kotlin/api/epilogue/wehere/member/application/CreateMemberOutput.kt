package api.epilogue.wehere.member.application

import api.epilogue.wehere.member.domain.Member

data class CreateMemberOutput(
    val status: CreateMemberStatus,
    val member: Member
) {
    companion object {
        fun success(member: Member) =
            CreateMemberOutput(
                CreateMemberStatus.SUCCESS,
                member
            )

        fun alreadyCreated(member: Member) =
            CreateMemberOutput(
                CreateMemberStatus.ALREADY_CREATED,
                member
            )
    }
}