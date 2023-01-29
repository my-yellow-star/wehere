package api.epilogue.wehere.nostalgia.controller

import api.epilogue.wehere.nostalgia.application.NostalgiaStatisticService
import java.util.UUID
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/nostalgia/statistics")
class NostalgiaStatisticController(
    private val statisticService: NostalgiaStatisticService
) {
    @GetMapping("/{memberId}")
    fun getSummary(@PathVariable memberId: UUID) =
        statisticService.getSummary(memberId)
}