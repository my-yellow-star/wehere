package api.epilogue.wehere.nostalgia.infra

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "googleMapClient",
    url = "https://maps.googleapis.com/maps/api",
)
interface GoogleMapClient {
    @GetMapping(value = ["/geocode/json"])
    fun getAddress(
        @RequestParam(value = "key") apiKey: String,
        @RequestParam(value = "latlng") latLng: String,
        @RequestParam(value = "language") language: String? = null,
        @RequestParam(value = "location_type") locationType: String = "APPROXIMATE"
    ): GoogleAddressResponse
}

data class GoogleAddressResponse(
    val results: List<GoogleAddressResult>
)

data class GoogleAddressResult(
    val address_components: List<Any>,
    val formatted_address: String,
    val geometry: Any
)