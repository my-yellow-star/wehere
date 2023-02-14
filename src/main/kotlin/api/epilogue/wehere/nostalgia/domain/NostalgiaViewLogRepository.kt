package api.epilogue.wehere.nostalgia.domain

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface NostalgiaViewLogRepository : JpaRepository<NostalgiaViewLog, UUID>