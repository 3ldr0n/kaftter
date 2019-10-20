package kaftter.repository;

import kaftter.domain.TweetEntity;
import kaftter.domain.TweetKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends CassandraRepository<TweetEntity, TweetKey> {}