package api.epilogue.wehere.nostalgia.domain

import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.nostalgia.domain.Nostalgia.NostalgiaVisibility
import java.util.UUID
import javax.persistence.criteria.CriteriaBuilder
import org.locationtech.jts.geom.Point
import org.springframework.data.jpa.domain.Specification

object NostalgiaSpec {
    fun memberIdEq(memberId: UUID): Specification<Nostalgia> =
        Specification { root, _, builder ->
            builder.equal(root.get<Member>(Nostalgia::member.name).get<UUID>(Member::id.name), memberId)
        }

    fun visibilityEq(visibility: NostalgiaVisibility): Specification<Nostalgia> =
        Specification { root, _, builder ->
            builder.equal(root.get<NostalgiaVisibility>(Nostalgia::visibility.name), visibility)
        }

    fun filterVisible(memberId: UUID): Specification<Nostalgia> =
        memberIdEq(memberId).or(visibilityEq(NostalgiaVisibility.ALL))

    fun distanceLessThan(point: Point, distance: Double): Specification<Nostalgia> {
        return Specification { root, _, builder ->
            builder.lessThan(
                builder.function(
                    "distance_sphere",
                    Double::class.java,
                    root.get<Point>(Nostalgia::location.name),
                    builder.literal(point)
                ),
                distance
            )
        }
    }

    fun orderByDistance(point: Point): Specification<Nostalgia> {
        return Specification { root, query, builder ->
            query.orderBy(
                builder.asc(
                    builder.function(
                        "distance_sphere",
                        Double::class.java,
                        root.get<Point>(Nostalgia::location.name),
                        builder.literal(point)
                    )
                )
            )
            builder.noCondition()
        }
    }

    private fun CriteriaBuilder.noCondition() =
        equal(literal(1), 1)
}
