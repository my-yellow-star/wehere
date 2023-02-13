package api.epilogue.wehere.search.infra

import api.epilogue.wehere.config.KakaoProperties
import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.nostalgia.infra.KakaoMapClient
import api.epilogue.wehere.search.domain.LocationSearchCountry
import api.epilogue.wehere.search.domain.LocationSearchInput
import api.epilogue.wehere.search.domain.LocationSearchItem
import api.epilogue.wehere.search.domain.LocationSearchResult
import api.epilogue.wehere.search.domain.LocationSearcher
import org.springframework.stereotype.Component

@Component
class LocationSearcherImpl(
    private val kakaoMapClient: KakaoMapClient,
    private val kakaoProperties: KakaoProperties
) : LocationSearcher {
    override fun search(input: LocationSearchInput): LocationSearchResult =
        when (input.country) {
            LocationSearchCountry.KOREA -> searchKR(input.keyword, input.page)
            LocationSearchCountry.OTHER -> TODO()
        }

    private fun searchKR(keyword: String, page: Int): LocationSearchResult {
        val response = kakaoMapClient
            .searchKeyword(keyword, page, kakaoProperties.mapApiKey)
        val items = response.documents.map {
            LocationSearchItem(
                name = it.place_name,
                address = it.road_address_name,
                location = Location(it.y.toDouble(), it.x.toDouble()),
                category = it.category
            )
        }
        return LocationSearchResult(
            total = response.meta.pageable_count,
            items = items,
            isEnd = response.meta.is_end
        )
    }
}