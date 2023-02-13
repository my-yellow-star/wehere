package api.epilogue.wehere.nostalgia.domain

interface Geocoder {
    fun locationToAddress(location: Location): AddressResult
}

data class AddressResult(
    val en: String?,
    val ko: String?
)