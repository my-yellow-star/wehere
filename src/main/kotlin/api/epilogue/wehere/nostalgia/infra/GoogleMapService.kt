package api.epilogue.wehere.nostalgia.infra

import api.epilogue.wehere.config.GoogleApiProperties
import api.epilogue.wehere.nostalgia.domain.Location
import org.springframework.stereotype.Component

@Component
class GoogleMapService(
    private val googleMapClient: GoogleMapClient,
    private val googleApiProperties: GoogleApiProperties
) {
    fun locationToAddress(location: Location) =
        getAddress(location)?.results?.firstOrNull()?.formatted_address

    fun locationToAddressKR(location: Location) =
        getAddress(location, "ko")?.results?.firstOrNull()?.formatted_address

    private fun getAddress(location: Location, language: String? = null) =
        kotlin.runCatching {
            googleMapClient.getAddress(
                apiKey = googleApiProperties.mapApiKey,
                latLng = "${location.latitude},${location.longitude}",
                language = language
            )
        }.getOrNull()
}