package ru.matushov.springcourse.Project2Boot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.matushov.springcourse.Project2Boot.models.Book;
import ru.matushov.springcourse.Project2Boot.models.Person;
import ru.matushov.springcourse.Project2Boot.repositories.PeopleRepository;

import jakarta.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;//Внедряем репозиторий
    private  final EntityManager entityManager;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntityManager entityManager) {
        this.peopleRepository = peopleRepository;
        this.entityManager = entityManager;
    }

    public List<Person> findALl() {
        return peopleRepository.findAll();
    }

    public List<Book>getBookList(int id) {
        Optional<Person> person =peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            return  person.get().getBooks();
        }else {
            return Collections.emptyList();
        }
    }





    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return  foundPerson.orElse(null);
    }

    @Transactional
    public void save (Person person) {
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setUser_id(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }


}
