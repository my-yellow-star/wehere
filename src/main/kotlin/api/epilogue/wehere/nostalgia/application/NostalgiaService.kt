package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.kernel.RegexUtils
import api.epilogue.wehere.member.domain.MemberRepository
import api.epilogue.wehere.nostalgia.domain.Geocoder
import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.nostalgia.domain.NostalgiaRepository
import api.epilogue.wehere.nostalgia.domain.NostalgiaSpec
import api.epilogue.wehere.nostalgia.domain.NostalgiaStatistic
import api.epilogue.wehere.nostalgia.domain.NostalgiaStatisticRepository
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NostalgiaService(
    private val memberRepository: MemberRepository,
    private val nostalgiaRepository: NostalgiaRepository,
    private val statisticRepository: NostalgiaStatisticRepository,
    private val geocoder: Geocoder
) {
    @Transactional
    fun create(memberId: UUID, input: CreateNostalgiaInput): CreateNostalgiaOutput {
        val member = memberRepository.getReferenceById(memberId)
        val nostalgia = input.toNostalgia(member)
        val location = Location(
            input.latitude,
            input.longitude
        )
        val addressEn = input.address?.takeUnless { RegexUtils.containsKorean(it) }
        val addressKo = input.address?.takeIf {
            RegexUtils.containsKorean(it) || !location.isInKorea
        }
        nostalgia.updateAddress(
            en = addressEn ?: geocoder.locationToAddressEn(location),
            ko = addressKo ?: geocoder.locationToAddressKo(location)
        )
        nostalgiaRepository.save(nostalgia)
        val statistic = statisticRepository.findByMemberId(memberId)
            ?: NostalgiaStatistic(member)
        statistic.onNostalgiaCreated(nostalgia)
        statisticRepository.save(statistic)
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
            markerColor = input.markerColor ?: markerColor
            isRealLocation = input.isRealLocation ?: isRealLocation
            memorizedAt = input.memorizedAt ?: memorizedAt
            if (input.location != null) {
                nostalgia.updateLocation(input.location)
                val addressEn = input.address?.takeUnless { RegexUtils.containsKorean(it) }
                val addressKo = input.address?.takeIf {
                    RegexUtils.containsKorean(it) || !input.location.isInKorea
                }
                nostalgia.updateAddress(
                    en = addressEn ?: geocoder.locationToAddressEn(input.location),
                    ko = addressKo ?: geocoder.locationToAddressKo(input.location)
                )
            }
            input.images?.let { updateMedia(it) }
        }
    }

    @Transactional
    fun delete(memberId: UUID, nostalgiaId: UUID) {
        findNostalgiaOrThrows(memberId, nostalgiaId)
            .delete()
        statisticRepository
            .findByMemberId(memberId)
            ?.onNostalgiaDeleted()
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