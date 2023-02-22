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

    @GetMapping(value = ["geocode/json"])
    fun getLocation(
        @RequestParam(value = "key") apiKey: String,
        @RequestParam(value = "place_id") placeId: String
    ): GoogleAddressResponse

    @GetMapping(value = ["/place/findplacefromtext/json"])
    fun findPlace(
        @RequestParam(value = "key") apiKey: String,
        @RequestParam(value = "input") input: String,
        @RequestParam(value = "inputtype") type: String = "textquery",
        @RequestParam(value = "fields") fields: String = "formatted_address,name,geometry"
    ): GoogleFindPlaceResponse

    @GetMapping(value = ["place/autocomplete/json"])
    fun autoComplete(
        @RequestParam(value = "key") apiKey: String,
        @RequestParam(value = "input") input: String,
        @RequestParam(value = "radius") radius: Int = 50000
    ): GoogleAutoCompleteResponse
}

data class GoogleAddressResponse(
    val results: List<GoogleAddressResult>
)

data class GoogleAddressResult(
    val formatted_address: String,
    val geometry: GoogleGeometry
)

data class GoogleFindPlaceResponse(
    val candidates: List<GoogleFindPlaceResult>
)

data class GoogleFindPlaceResult(
    val name: String,
    val formatted_address: String,
    val geometry: GoogleGeometry
)

data class GoogleGeometry(
    val location: GoogleGeometryLocation
)

data class GoogleGeometryLocation(
    val lng: Double,
    val lat: Double
)

data class GoogleAutoCompleteResponse(
    val predictions: List<GoogleAutoCompletePrediction>
)

data class GoogleAutoCompletePrediction(
    val place_id: String,
    val description: String,
    val types: List<String>,
    val structured_formatting: GoogleStructuredFormatting
)

data class GoogleStructuredFormatting(
    val main_text: String,
    val secondary_text: String?
)