package api.epilogue.wehere.member.application

import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.member.domain.Member.MemberGrade
import java.util.UUID

data class CreateMemberOutput(
    val status: CreateMemberStatus,
    val id: UUID,
    val grade: MemberGrade
) {
    companion object {
        fun success(member: Member) =
            CreateMemberOutput(
                CreateMemberStatus.SUCCESS,
                member.id,
                member.grade
            )

        fun alreadyCreated(member: Member) =
            CreateMemberOutput(
                CreateMemberStatus.ALREADY_CREATED,
                member.id,
                member.grade
            )
    }
}