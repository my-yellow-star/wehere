package api.epilogue.wehere.member.application

import api.epilogue.wehere.client.PageResponse
import api.epilogue.wehere.nostalgia.application.NostalgiaListOutput
import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaSpec
import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetBookmarksUseCase(
    private val nostalgiaRepository: NostalgiaRepository
) {
    @Transactional(readOnly = true)
    fun execute(principalId: UUID, memberId: UUID, current: Location, pageable: Pageable) =
        run {
            val spec = NostalgiaSpec.bookmarkedBy(memberId)
                .and(NostalgiaSpec.filterVisible(principalId))
            val result = nostalgiaRepository.findAll(spec, pageable)
            PageResponse.of(result) { nostalgia -> NostalgiaListOutput.of(nostalgia, current) }
        }
}