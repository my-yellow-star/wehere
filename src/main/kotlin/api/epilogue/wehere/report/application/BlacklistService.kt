package api.epilogue.wehere.report.application

import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.member.domain.MemberRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import api.epilogue.wehere.report.domain.MemberBlacklist
import api.epilogue.wehere.report.domain.MemberBlacklistRepository
import api.epilogue.wehere.report.domain.NostalgiaBlacklist
import api.epilogue.wehere.report.domain.NostalgiaBlacklistRepository
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BlacklistService(
    private val memberBlacklistRepository: MemberBlacklistRepository,
    private val nostalgiaBlacklistRepository: NostalgiaBlacklistRepository,
    private val nostalgiaRepository: NostalgiaRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun createMember(memberId: UUID, targetId: UUID) {
        if (memberId == targetId)
            throw ApiError(ErrorCause.INVALID_REQUEST)
        if (memberBlacklistRepository.findByMemberIdAndTargetId(memberId, targetId) != null)
            throw ApiError(ErrorCause.INVALID_REQUEST)
        val member = memberRepository.getReferenceById(memberId)
        memberBlacklistRepository.save(
            MemberBlacklist(member, targetId)
        )
    }

    @Transactional
    fun deleteMember(memberId: UUID, targetId: UUID) {
        if (memberId == targetId)
            throw ApiError(ErrorCause.INVALID_REQUEST)
        val blacklist =
            memberBlacklistRepository
                .findByMemberIdAndTargetId(memberId, targetId)
                ?: throw ApiError(ErrorCause.INVALID_REQUEST)
        memberBlacklistRepository.delete(blacklist)
    }

    @Transactional
    fun createNostalgia(memberId: UUID, targetId: UUID) {
        val nostalgia = nostalgiaRepository
            .findByIdOrNull(targetId)
            ?: throw ApiError(ErrorCause.ENTITY_NOT_FOUND)
        if (nostalgia.member.id == memberId)
            throw ApiError(ErrorCause.INVALID_REQUEST)
        nostalgiaBlacklistRepository.save(
            NostalgiaBlacklist(memberId, nostalgia)
        )
    }

    @Transactional
    fun deleteNostalgia(memberId: UUID, targetId: UUID) {
        val blacklist = nostalgiaBlacklistRepository
            .findByMemberIdAndNostalgiaId(memberId, targetId)
            ?: throw ApiError(ErrorCause.INVALID_REQUEST)
        nostalgiaBlacklistRepository.delete(blacklist)
    }
}