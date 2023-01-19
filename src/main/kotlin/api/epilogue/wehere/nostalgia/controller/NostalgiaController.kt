package api.epilogue.wehere.nostalgia.controller

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.client.PageRequest
import api.epilogue.wehere.nostalgia.application.CreateNostalgiaInput
import api.epilogue.wehere.nostalgia.application.Location
import api.epilogue.wehere.nostalgia.application.NostalgiaGetter
import api.epilogue.wehere.nostalgia.application.NostalgiaService
import api.epilogue.wehere.nostalgia.application.UpdateNostalgiaInput
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import java.util.UUID
import org.springframework.data.domain.Sort
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/nostalgia")
class NostalgiaController(
    private val service: NostalgiaService,
    private val getter: NostalgiaGetter
) {
    @GetMapping
    fun get(
        @AuthenticationPrincipal principal: MemberPrincipal,
        pageRequest: PageRequest,
        condition: NostalgiaCondition,
        current: Location?,
        maxDistance: Int?
    ) =
        when (condition) {
            NostalgiaCondition.RECENT -> getter.getListRecent(
                principal.id,
                current,
                pageRequest.toPageable(Sort.Order.asc(Nostalgia::createdAt.name))
            )
            NostalgiaCondition.MEMBER -> getter.getListByMember(
                principal.id,
                current,
                pageRequest.toPageable()
            )
            NostalgiaCondition.AROUND -> getter.getListAround(
                principal.id,
                current!!,
                maxDistance,
                pageRequest.toPageable()
            )
        }

    @PostMapping
    fun create(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestBody input: CreateNostalgiaInput
    ) = service.create(principal.id, input)

    @PatchMapping("/{nostalgiaId}")
    fun patch(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable nostalgiaId: UUID,
        @RequestBody input: UpdateNostalgiaInput
    ) {
        service.update(principal.id, nostalgiaId, input)
    }

    @DeleteMapping("/{nostalgiaId}")
    fun delete(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable nostalgiaId: UUID
    ) {
        service.delete(principal.id, nostalgiaId)
    }
}