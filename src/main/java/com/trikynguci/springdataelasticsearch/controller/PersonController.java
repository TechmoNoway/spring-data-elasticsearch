package com.trikynguci.springdataelasticsearch.controller;

import com.trikynguci.springdataelasticsearch.document.Person;
import com.trikynguci.springdataelasticsearch.service.PersonService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public void save(@RequestBody final Person person) {
        personService.save(person);
    }

    public Person getById(@PathVariable final String id) {
        return personService.findById(id);
    }
}

