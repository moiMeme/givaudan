package com.givaudan.contacts.repository.search;

import com.givaudan.contacts.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.stream.Stream;

public interface UserSearchRepository extends ElasticsearchRepository<User, Long>, UserSearchRepositoryInternal {}

interface UserSearchRepositoryInternal {
    Stream<User> search(String query);
}
@RequiredArgsConstructor
class UserSearchRepositoryInternalImpl implements UserSearchRepositoryInternal {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Stream<User> search(String query) {
        StringQuery stringQuery = new StringQuery(query);
        return elasticsearchOperations.search(stringQuery, User.class).map(SearchHit::getContent).stream();
    }
}
