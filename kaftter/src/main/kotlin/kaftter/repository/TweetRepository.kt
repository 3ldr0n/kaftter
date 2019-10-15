package kaftter.repository

import kaftter.domain.TweetEntity
import kaftter.domain.TweetKey
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository

@Repository
interface TweetRepository : CassandraRepository<TweetEntity, TweetKey>