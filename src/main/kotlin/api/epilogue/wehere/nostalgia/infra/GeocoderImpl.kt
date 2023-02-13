package api.epilogue.wehere.nostalgia.infra

import api.epilogue.wehere.kernel.LocationUtils
import api.epilogue.wehere.nostalgia.domain.AddressResult
import api.epilogue.wehere.nostalgia.domain.Geocoder
import api.epilogue.wehere.nostalgia.domain.Location
import org.springframework.stereotype.Component

@Component
class GeocoderImpl(
    private val googleMapService: GoogleMapService,
    private val kakaoMapService: KakaoMapService
) : Geocoder {
    override fun locationToAddress(location: Location): AddressResult {
        val address = googleMapService.locationToAddress(location)
        val addressKR =
            if (LocationUtils.isInKorea(location.latitude, location.longitude))
                kakaoMapService.locationToAddress(location)
            else
                address
        return AddressResult(
            en = address,
            ko = addressKR
        )
    }
}