package com.givaudan.contacts.repository.search;

import com.givaudan.contacts.domain.Contact;
import com.givaudan.contacts.repository.jpa.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.stream.Collectors;


public interface ContactSearchRepository extends ElasticsearchRepository<Contact, Long>, ContactSearchRepositoryInternal {}

interface ContactSearchRepositoryInternal {
    Page<Contact> search(String query, Pageable pageable);

    Page<Contact> search(Query query);

    void index(Contact entity);
}
@RequiredArgsConstructor
class ContactSearchRepositoryInternalImpl implements ContactSearchRepositoryInternal {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ContactRepository repository;

    @Override
    public Page<Contact> search(String query, Pageable pageable) {
        StringQuery stringQuery = new StringQuery(query, pageable);
        return search(stringQuery);
    }

    @Override
    public Page<Contact> search(Query query) {
        SearchHits<Contact> searchHits = elasticsearchOperations.search(query, Contact.class);
        List<Contact> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(Contact entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchOperations::save);
    }
}
