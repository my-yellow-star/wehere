package api.epilogue.wehere.nostalgia.domain

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class NostalgiaMedium(
    @ManyToOne
    @JoinColumn(name = "nostalgiaId")
    val nostalgia: Nostalgia,
    val sortIndex: Int,
    val url: String,
    val thumbnail: String = url,
    @Enumerated(EnumType.STRING)
    val type: MediumType = MediumType.IMAGE,
    @Id
    val id: UUID = UUID.randomUUID()
) {
    enum class MediumType {
        IMAGE,
        VIDEO
    }
}