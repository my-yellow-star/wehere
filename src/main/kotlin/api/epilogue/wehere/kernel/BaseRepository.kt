package api.epilogue.wehere.kernel

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T : BasePersistable> : JpaRepository<T, UUID>, JpaSpecificationExecutor<T>