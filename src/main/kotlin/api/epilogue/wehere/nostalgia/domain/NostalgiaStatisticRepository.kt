package api.epilogue.wehere.nostalgia.domain

import api.epilogue.wehere.kernel.BaseRepository
import java.util.UUID

interface NostalgiaStatisticRepository : BaseRepository<NostalgiaStatistic> {
    fun findByMemberId(memberId: UUID): NostalgiaStatistic?
}