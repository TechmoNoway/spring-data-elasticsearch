package com.trikynguci.springdataelasticsearch.repository;

import com.trikynguci.springdataelasticsearch.document.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ElasticsearchRepository<Person, String> {

}
