package api.epilogue.wehere.nostalgia.domain

import api.epilogue.wehere.kernel.BasePersistable
import api.epilogue.wehere.kernel.LocationUtils
import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.report.domain.NostalgiaBlacklist
import java.time.Instant
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OrderBy
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
    var thumbnailUrl: String? = null,
    @Enumerated(EnumType.STRING)
    var markerColor: MarkerColor = MarkerColor.BLUE_GREEN,
    var isRealLocation: Boolean = true,
    var memorizedAt: Instant = Instant.now()
) : BasePersistable() {
    @Column(columnDefinition = "geometry(point)")
    var location: Point = LocationUtils.toPoint(latitude, longitude)
        protected set
    var address: String? = ""
    var addressKo: String? = ""

    @OneToMany(mappedBy = "nostalgia", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("sortIndex")
    val media: MutableList<NostalgiaMedium> = mutableListOf()

    @OneToMany(mappedBy = "nostalgia")
    val bookmarks: List<NostalgiaBookmark> = listOf()

    @OneToMany(mappedBy = "nostalgia")
    val blacklists: List<NostalgiaBlacklist> = listOf()

    enum class NostalgiaVisibility {
        OWNER,
        FRIEND,
        ALL,
        NONE // deleted
    }

    fun addMedia(urls: List<String>) {
        media.addAll(
            urls.mapIndexed { index, url ->
                NostalgiaMedium(this, index, url)
            }
        )
    }

    fun updateMedia(urls: List<String>) {
        media.clear()
        addMedia(urls)
    }

    fun updateAddress(en: String?, ko: String?) {
        address = en
        addressKo = ko
    }

    fun updateLocation(location: Location) {
        this.location = LocationUtils.toPoint(location.latitude, location.longitude)
    }

    fun delete() {
        visibility = NostalgiaVisibility.NONE
    }

    fun isVisible(member: Member) =
        visibility == NostalgiaVisibility.ALL ||
                this.member.id == member.id
}