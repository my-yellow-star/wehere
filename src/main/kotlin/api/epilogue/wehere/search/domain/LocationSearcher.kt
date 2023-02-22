package api.epilogue.wehere.search.domain

import api.epilogue.wehere.nostalgia.domain.Location

interface LocationSearcher {
    fun search(input: LocationSearchInput): LocationSearchResult
    fun getPlaceLocation(placeId: String): Location
}

data class LocationSearchInput(
    val keyword: String,
    val page: Int,
    val current: Location,
    val country: LocationSearchCountry
)

data class LocationSearchResult(
    val total: Int,
    val items: List<LocationSearchItem>,
    val isEnd: Boolean
)

data class LocationSearchItem(
    val id: String,
    val name: String,
    val address: String,
    val location: Location?,
    val distance: Int?,
    val category: String? = null
)

enum class LocationSearchCountry {
    KOREA,
    OTHER
}