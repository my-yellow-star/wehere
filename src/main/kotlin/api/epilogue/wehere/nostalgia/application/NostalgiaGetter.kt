package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.client.PageResponse
import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaSpec
import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NostalgiaGetter(
    private val repository: NostalgiaRepository,
    private val viewNostalgiaUseCase: ViewNostalgiaUseCase
) {
    @Transactional(readOnly = true)
    fun getListAround(
        principalId: UUID,
        memberId: UUID?,
        current: Location,
        target: Location,
        maxDistance: Double?,
        pageable: Pageable
    ): PageResponse<NostalgiaListOutput> {
        var spec = NostalgiaSpec.filterVisible(principalId)
            .and(NostalgiaSpec.orderByDistance(target.toPoint()))
        if (memberId != null) {
            spec = spec.and(NostalgiaSpec.memberIdEq(memberId))
        }
        if (maxDistance != null)
            spec = spec.and(NostalgiaSpec.distanceLessThan(target.toPoint(), maxDistance))
        val result = repository.findAll(spec, pageable)
        return PageResponse.of(result) {
            NostalgiaListOutput.of(it, current)
        }
    }

    @Transactional(readOnly = true)
    fun getListRecent(
        principalId: UUID,
        memberId: UUID?,
        current: Location,
        pageable: Pageable
    ): PageResponse<NostalgiaListOutput> {
        var spec = NostalgiaSpec.filterVisible(principalId)
        if (memberId != null) {
            spec = spec.and(NostalgiaSpec.memberIdEq(memberId))
        }
        val result = repository.findAll(spec, pageable)
        return PageResponse.of(result) {
            NostalgiaListOutput.of(it, current)
        }
    }

    @Transactional(readOnly = true)
    fun getDetail(
        memberId: UUID,
        nostalgiaId: UUID,
        current: Location,
    ): NostalgiaOutput {
        val nostalgia = repository.findByIdOrNull(nostalgiaId)
            ?: throw ApiError(ErrorCause.ENTITY_NOT_FOUND)
        if (!isVisible(memberId, nostalgia))
            throw ApiError(ErrorCause.NOT_OWNER)
        viewNostalgiaUseCase.execute(memberId, nostalgiaId)
        return NostalgiaOutput.of(nostalgia, memberId, current)
    }

    private fun isVisible(memberId: UUID, nostalgia: Nostalgia) =
        nostalgia.member.id == memberId || nostalgia.visibility == Nostalgia.NostalgiaVisibility.ALL
}