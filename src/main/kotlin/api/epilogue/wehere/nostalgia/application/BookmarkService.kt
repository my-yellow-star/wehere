package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.member.domain.MemberRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaBookmark
import api.epilogue.wehere.nostalgia.domain.NostalgiaBookmarkRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookmarkService(
    private val repository: NostalgiaBookmarkRepository,
    private val memberRepository: MemberRepository,
    private val nostalgiaRepository: NostalgiaRepository
) {
    @Transactional
    fun create(
        memberId: UUID,
        nostalgiaId: UUID
    ) {
        val member = memberRepository.getReferenceById(memberId)
        val nostalgia = nostalgiaRepository
            .findByIdOrNull(nostalgiaId)
            ?: throw ApiError(ErrorCause.ENTITY_NOT_FOUND)
        if (!nostalgia.isVisible(member))
            throw ApiError(ErrorCause.NOT_OWNER)
        val bookmark = repository.findByMemberIdAndNostalgiaId(memberId, nostalgiaId)
        if (bookmark != null)
            throw ApiError(ErrorCause.INVALID_REQUEST)
        repository.save(NostalgiaBookmark(member, nostalgia))
    }

    @Transactional
    fun delete(
        memberId: UUID,
        nostalgiaId: UUID
    ) {
        val bookmark = repository
            .findByMemberIdAndNostalgiaId(memberId, nostalgiaId)
            ?: throw ApiError(ErrorCause.INVALID_REQUEST)
        bookmark.delete()
    }
}