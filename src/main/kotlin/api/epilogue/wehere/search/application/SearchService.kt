package api.epilogue.wehere.search.application

import api.epilogue.wehere.client.PageResponse
import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.search.domain.LocationSearchCountry
import api.epilogue.wehere.search.domain.LocationSearchInput
import api.epilogue.wehere.search.domain.LocationSearcher
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val locationSearcher: LocationSearcher
) {
    fun searchLocation(
        keyword: String,
        page: Int?,
        current: Location,
        country: LocationSearchCountry?
    ) =
        locationSearcher
            .search(
                LocationSearchInput(
                    keyword, page ?: 1, current, country ?: LocationSearchCountry.KOREA
                )
            )
            .let {
                PageResponse(
                    total = it.total.toLong(),
                    items = it.items,
                    nextPage = if (it.isEnd) null else (page ?: 1) + 1
                )
            }

    fun searchPlaceLocation(
        placeId: String
    ) = locationSearcher.getPlaceLocation(placeId)
}