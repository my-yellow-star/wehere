package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.nostalgia.domain.NostalgiaStatisticRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class NostalgiaStatisticService(
    private val repository: NostalgiaStatisticRepository
) {
    fun getSummary(memberId: UUID) =
        repository.findByMemberId(memberId)
            ?.let {
                NostalgiaStatisticSummary(
                    it.total,
                    it.accumulatedDistance,
                    it.lastLocation?.let(Location.Companion::of)
                )
            }
            ?: NostalgiaStatisticSummary(0, .0)
}