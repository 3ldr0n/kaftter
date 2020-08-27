package kaftter.api.repository

import kaftter.api.domain.SummarizedTweetEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository

@Repository
interface SummarizedTweetRepository : CassandraRepository<SummarizedTweetEntity, Long> {
    fun findByUserId(userId: Long): SummarizedTweetEntity?
}
