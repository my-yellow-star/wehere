package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.nostalgia.domain.Location

data class NostalgiaStatisticSummary(
    val totalCount: Long,
    val accumulatedDistance: Double,
    val lastLocation: Location? = null
)