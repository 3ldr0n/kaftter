package kaftter.api.repository

import kaftter.api.domain.SummarizedTweetEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SummarizedTweetRepository : CassandraRepository<SummarizedTweetEntity, Long> {
    fun findByUserId(userId: Long): Optional<SummarizedTweetEntity>
}