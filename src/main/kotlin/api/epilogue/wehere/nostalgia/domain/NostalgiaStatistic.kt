package api.epilogue.wehere.nostalgia.domain

import api.epilogue.wehere.kernel.BasePersistable
import api.epilogue.wehere.member.domain.Member
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import org.locationtech.jts.geom.Point

@Entity
class NostalgiaStatistic(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    val member: Member,
    var total: Long = 0,
    var accumulatedDistance: Double = .0,
    var lastLocation: Point? = null
) : BasePersistable() {
    fun onNostalgiaCreated(nostalgia: Nostalgia) {
        total += 1
        lastLocation?.let {
            accumulatedDistance += Location.of(it).distance(Location.of(nostalgia.location))
        }
        lastLocation = nostalgia.location
    }
}