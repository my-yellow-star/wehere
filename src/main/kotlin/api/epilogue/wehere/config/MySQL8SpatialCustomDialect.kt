package api.epilogue.wehere.config

import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.spatial.dialect.mysql.MySQL8SpatialDialect
import org.hibernate.type.StandardBasicTypes

class MySQL8SpatialCustomDialect : MySQL8SpatialDialect() {
    init {
        registerFunction(
            "distance_sphere",
            StandardSQLFunction(
                "st_distance_sphere",
                StandardBasicTypes.DOUBLE
            )
        )
    }
}