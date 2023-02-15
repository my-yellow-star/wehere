package api.epilogue.wehere.nostalgia.domain

import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.nostalgia.domain.Nostalgia.NostalgiaVisibility
import api.epilogue.wehere.report.domain.NostalgiaBlacklist
import java.util.UUID
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root
import org.locationtech.jts.geom.Point
import org.springframework.data.jpa.domain.Specification

object NostalgiaSpec {
    val memberIdEqPredicate =
        { root: Root<Nostalgia>, builder: CriteriaBuilder, memberId: UUID ->
            builder.equal(root.get<Member>(Nostalgia::member.name).get<UUID>(Member::id.name), memberId)
        }
    val visibilityEqPredicate =
        { root: Root<Nostalgia>, builder: CriteriaBuilder, visibility: NostalgiaVisibility ->
            builder.equal(root.get<NostalgiaVisibility>(Nostalgia::visibility.name), visibility)
        }

    fun memberIdEq(memberId: UUID): Specification<Nostalgia> =
        Specification { root, _, builder ->
            memberIdEqPredicate(root, builder, memberId)
        }

    fun memberIdNotIn(memberIds: List<UUID>): Specification<Nostalgia> =
        Specification { root, _, builder ->
            if (memberIds.isNotEmpty()) {
                root.get<Member>(Nostalgia::member.name).get<UUID>(Member::id.name).`in`(memberIds).not()
            } else {
                builder.noCondition()
            }
        }

    fun visibilityEq(visibility: NostalgiaVisibility): Specification<Nostalgia> =
        Specification { root, _, builder ->
            visibilityEqPredicate(root, builder, visibility)
        }

    fun filterVisible(memberId: UUID): Specification<Nostalgia> =
        Specification { root, query, builder ->
            val blacklist = root
                .join<Nostalgia, NostalgiaBlacklist>(Nostalgia::blacklists.name, JoinType.LEFT)
            query.groupBy(root.get<UUID>("id"))
            builder.and(
                builder.or(
                    memberIdEqPredicate(root, builder, memberId),
                    visibilityEqPredicate(root, builder, NostalgiaVisibility.ALL)
                ),
                builder.or(
                    builder.isNull(blacklist.get<UUID>(NostalgiaBlacklist::memberId.name)),
                    builder.notEqual(blacklist.get<UUID>(NostalgiaBlacklist::memberId.name), memberId)
                )
            )
        }

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
