package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.member.domain.MemberRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaSpec
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NostalgiaService(
    private val memberRepository: MemberRepository,
    private val nostalgiaRepository: NostalgiaRepository
) {
    @Transactional
    fun create(memberId: UUID, input: CreateNostalgiaInput): CreateNostalgiaOutput {
        val member = memberRepository.getReferenceById(memberId)
        val nostalgia = input.toNostalgia(member)
        nostalgiaRepository.save(nostalgia)
        return CreateNostalgiaOutput(nostalgia.id)
    }

    @Transactional
    fun update(memberId: UUID, nostalgiaId: UUID, input: UpdateNostalgiaInput) {
        val nostalgia = findNostalgiaOrThrows(memberId, nostalgiaId)
        nostalgia.apply {
            description = input.description ?: description
            title = input.title ?: title
            visibility = input.visibility ?: visibility
            thumbnailUrl = input.images?.firstOrNull() ?: thumbnailUrl
            input.images?.let { updateMedia(it) }
        }
    }

    @Transactional
    fun delete(memberId: UUID, nostalgiaId: UUID) {
        findNostalgiaOrThrows(memberId, nostalgiaId)
            .delete()
    }

    @Transactional
    fun deleteAll(memberId: UUID) {
        nostalgiaRepository.findAll(NostalgiaSpec.memberIdEq(memberId))
            .forEach {
                it.delete()
            }
    }

    private fun findNostalgiaOrThrows(memberId: UUID, nostalgiaId: UUID) =
        nostalgiaRepository.findByIdOrNull(nostalgiaId)
            ?.also {
                if (memberId != it.member.id)
                    throw ApiError(ErrorCause.NOT_OWNER)
            }
            ?: throw ApiError(ErrorCause.ENTITY_NOT_FOUND)
}