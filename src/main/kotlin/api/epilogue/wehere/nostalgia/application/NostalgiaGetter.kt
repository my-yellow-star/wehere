package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.client.PageResponse
import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaSpec
import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NostalgiaGetter(
    private val repository: NostalgiaRepository
) {
    @Transactional(readOnly = true)
    fun getListAround(
        memberId: UUID,
        current: Location,
        maxDistance: Double?,
        pageable: Pageable
    ): PageResponse<NostalgiaListOutput> {
        var spec = NostalgiaSpec.filterVisible(memberId)
            .and(NostalgiaSpec.orderByDistance(current.toPoint()))
        if (maxDistance != null)
            spec = spec.and(NostalgiaSpec.distanceLessThan(current.toPoint(), maxDistance))
        val result = repository.findAll(spec, pageable)
        return PageResponse.of(result) {
            NostalgiaListOutput.of(it, current)
        }
    }

    @Transactional(readOnly = true)
    fun getListRecent(
        memberId: UUID,
        current: Location?,
        pageable: Pageable
    ): PageResponse<NostalgiaListOutput> {
        val spec = NostalgiaSpec.filterVisible(memberId)
        val result = repository.findAll(spec, pageable)
        return PageResponse.of(result) {
            NostalgiaListOutput.of(it, current)
        }
    }

    @Transactional(readOnly = true)
    fun getListByMember(
        memberId: UUID,
        current: Location?,
        pageable: Pageable
    ): PageResponse<NostalgiaListOutput> {
        val spec = NostalgiaSpec.memberIdEq(memberId)
        val result = repository.findAll(spec, pageable)
        return PageResponse.of(result) {
            NostalgiaListOutput.of(it, current)
        }
    }
}