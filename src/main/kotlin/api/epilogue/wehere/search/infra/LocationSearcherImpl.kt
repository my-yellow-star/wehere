package api.epilogue.wehere.search.infra

import api.epilogue.wehere.config.GoogleApiProperties
import api.epilogue.wehere.config.KakaoProperties
import api.epilogue.wehere.kernel.LocationUtils
import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.nostalgia.infra.GoogleMapClient
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
    private val kakaoProperties: KakaoProperties,
    private val googleMapClient: GoogleMapClient,
    private val googleApiProperties: GoogleApiProperties
) : LocationSearcher {
    override fun search(input: LocationSearchInput): LocationSearchResult =
        when (input.country) {
            LocationSearchCountry.KOREA -> searchKR(input)
            LocationSearchCountry.OTHER -> searchEn(input)
        }

    override fun getPlaceLocation(placeId: String): Location {
        val geometry = googleMapClient
            .getLocation(googleApiProperties.mapApiKey, placeId)
            .results[0]
            .geometry
        return Location(geometry.location.lat, geometry.location.lng)
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
            val location = Location(it.y!!.toDouble(), it.x!!.toDouble())
            LocationSearchItem(
                id = "",
                name = it.getExistAddress().address_name,
                address = it.getExistAddress().address_name,
                location = location,
                distance = getDistance(location, input.current).toInt(),
                category = null
            )
        } + keywordResponse.documents.map {
            val location = Location(it.y.toDouble(), it.x.toDouble())
            LocationSearchItem(
                id = it.id,
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

    private fun searchEn(input: LocationSearchInput): LocationSearchResult {
        val response = googleMapClient.autoComplete(
            apiKey = googleApiProperties.mapApiKey,
            input = input.keyword
        )
        val items = response.predictions.map {
            LocationSearchItem(
                id = it.place_id,
                name = it.structured_formatting.main_text,
                address = it.structured_formatting.secondary_text ?: "",
                location = null,
                distance = null,
                category = it.types.firstOrNull()
            )
        }
        return LocationSearchResult(
            total = items.size,
            items = items,
            isEnd = true
        )
    }

    private fun getDistance(location1: Location, location2: Location) =
        LocationUtils.calculateDistance(
            location1.latitude,
            location1.longitude,
            location2.latitude,
            location2.longitude
        )
}