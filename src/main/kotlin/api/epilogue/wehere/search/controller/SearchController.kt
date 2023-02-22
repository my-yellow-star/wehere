package api.epilogue.wehere.search.controller

import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.search.application.SearchService
import api.epilogue.wehere.search.domain.LocationSearchCountry
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/search")
class SearchController(
    private val searchService: SearchService
) {
    @GetMapping("/locations")
    fun searchLocation(
        keyword: String,
        page: Int?,
        current: Location,
        @RequestParam(required = false) country: LocationSearchCountry?
    ) =
        searchService.searchLocation(keyword, page, current, country)

    @GetMapping("/places/{placeId}/location")
    fun searchPlaceLocation(
        @PathVariable placeId: String
    ) =
        searchService.searchPlaceLocation(placeId)
}