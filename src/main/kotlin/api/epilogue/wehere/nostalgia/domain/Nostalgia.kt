package api.epilogue.wehere.nostalgia.domain

import api.epilogue.wehere.kernel.BasePersistable
import api.epilogue.wehere.member.domain.Member
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import org.hibernate.annotations.Where
import org.locationtech.jts.geom.Point

@Entity
@Where(clause = "visibility != 'NONE'")
class Nostalgia(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    val member: Member,
    var title: String,
    var description: String,
    @Enumerated(EnumType.STRING)
    var visibility: NostalgiaVisibility,
    latitude: Double,
    longitude: Double,
    var thumbnailUrl: String? = null
) : BasePersistable() {
    @Column(columnDefinition = "geometry(point)")
    val location: Point = LocationFactory.of(latitude, longitude)

    enum class NostalgiaVisibility {
        OWNER,
        FRIEND,
        ALL,
        NONE // deleted
    }

    fun delete() {
        visibility = NostalgiaVisibility.NONE
    }
}