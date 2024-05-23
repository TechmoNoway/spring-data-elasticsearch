package com.trikynguci.springdataelasticsearch.service;

import com.trikynguci.springdataelasticsearch.document.Person;
import com.trikynguci.springdataelasticsearch.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void save(final Person person) {
        personRepository.save(person);
    }

    public Person findById(final String id) {
        return personRepository.findById(id).orElse(null);
    }

}
