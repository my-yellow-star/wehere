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
            LocationSearchCountry.KOREA -> searchKR(input)
            LocationSearchCountry.OTHER -> TODO()
        }

    private fun searchKR(input: LocationSearchInput): LocationSearchResult {
        val addressResponse = kakaoMapClient
            .searchAddress(
                keyword = input.keyword,
                page = 0,
                size = 3,
                longitude = input.current.longitude,
                latitude = input.current.latitude,
                apiKey = kakaoProperties.mapApiKey
            )
        val keywordResponse = kakaoMapClient
            .searchKeyword(
                keyword = input.keyword,
                page = input.page,
                longitude = input.current.longitude,
                latitude = input.current.latitude,
                apiKey = kakaoProperties.mapApiKey
            )
        val items = addressResponse.documents.map {
            val location = Location(it.y.toDouble(), it.x.toDouble())
            LocationSearchItem(
                name = it.getExistAddress().address_name,
                address = it.getExistAddress().address_name,
                location = location,
                distance = null,
                category = null
            )
        } + keywordResponse.documents.map {
            val location = Location(it.y.toDouble(), it.x.toDouble())
            LocationSearchItem(
                name = it.place_name,
                address = it.road_address_name,
                location = location,
                distance = it.distance.toInt(),
                category = it.category
            )
        }
        return LocationSearchResult(
            total = keywordResponse.meta.pageable_count,
            items = items,
            isEnd = keywordResponse.meta.is_end
        )
    }
}