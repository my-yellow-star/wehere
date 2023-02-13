package api.epilogue.wehere.nostalgia.infra

import api.epilogue.wehere.config.KakaoProperties
import api.epilogue.wehere.nostalgia.domain.Location
import org.springframework.stereotype.Component

@Component
class KakaoMapService(
    private val kakaoMapClient: KakaoMapClient,
    private val kakaoProperties: KakaoProperties
) {
    fun locationToAddress(location: Location) =
        kotlin.runCatching {
            kakaoMapClient
                .getAddress(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    apiKey = kakaoProperties.mapApiKey
                )
                .documents
                .firstOrNull()
                ?.address
                ?.let {
                    "${it.region_1depth_name} ${it.region_2depth_name} ${it.region_3depth_name}"
                }
        }.getOrNull()
}