package api.epilogue.wehere.nostalgia.domain

interface Geocoder {
    fun locationToAddress(location: Location): AddressResult

    fun locationToAddressKo(location: Location): String?

    fun locationToAddressEn(location: Location): String?
}

data class AddressResult(
    val en: String?,
    val ko: String?
)