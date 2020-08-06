package kaftter.api.repository

import kaftter.api.domain.TweetEntity
import kaftter.api.domain.TweetKey
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository

@Repository
interface TweetRepository : CassandraRepository<TweetEntity, TweetKey>