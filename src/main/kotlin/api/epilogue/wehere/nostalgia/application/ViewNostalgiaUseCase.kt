package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.nostalgia.domain.NostalgiaViewLog
import api.epilogue.wehere.nostalgia.domain.NostalgiaViewLogRepository
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class ViewNostalgiaUseCase(
    private val nostalgiaViewLogRepository: NostalgiaViewLogRepository
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun execute(memberId: UUID, nostalgiaId: UUID) {
        nostalgiaViewLogRepository.save(
            NostalgiaViewLog(memberId, nostalgiaId)
        )
    }
}