package api.epilogue.wehere.nostalgia.infra

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "kakaoMapClient",
    url = "https://dapi.kakao.com",
)
interface KakaoMapClient {
    @GetMapping(value = ["/v2/local/geo/coord2address"])
    fun getAddress(
        @RequestParam(value = "x") longitude: Double,
        @RequestParam(value = "y") latitude: Double,
        @RequestHeader(value = "Authorization") apiKey: String
    ): KakaoAddressResponse

    @GetMapping(value = ["/v2/local/search/keyword"])
    fun searchKeyword(
        @RequestParam(value = "query") keyword: String,
        @RequestParam(value = "page") page: Int,
        @RequestParam(value = "x") longitude: Double,
        @RequestParam(value = "y") latitude: Double,
        @RequestHeader(value = "Authorization") apiKey: String,
    ): KakaoSearchKeywordResponse

    @GetMapping(value = ["/v2/local/search/address"])
    fun searchAddress(
        @RequestParam(value = "query") keyword: String,
        @RequestParam(value = "page") page: Int,
        @RequestParam(value = "size") size: Int,
        @RequestParam(value = "x") longitude: Double,
        @RequestParam(value = "y") latitude: Double,
        @RequestHeader(value = "Authorization") apiKey: String
    ): KakaoAddressResponse
}

data class KakaoAddressResponse(
    val documents: List<KakaoAddressDocument>
)

data class KakaoAddressDocument(
    val address: KakaoAddressDetail?,
    val road_address: KakaoAddressDetail?,
    val x: String?,
    val y: String?
) {
    fun getExistAddress() =
        (address ?: road_address)!!
}

data class KakaoAddressDetail(
    val address_name: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String
)

data class KakaoSearchKeywordResponse(
    val meta: KakaoSearchKeywordMeta,
    val documents: List<KakaoSearchKeywordDocument>
)

data class KakaoSearchKeywordMeta(
    val is_end: Boolean,
    val pageable_count: Int
)

data class KakaoSearchKeywordDocument(
    val id: String,
    val category_name: String,
    val place_name: String,
    val road_address_name: String,
    val distance: String,
    val x: String,
    val y: String
) {
    val category: String = category_name.split(">").last().trim()
}