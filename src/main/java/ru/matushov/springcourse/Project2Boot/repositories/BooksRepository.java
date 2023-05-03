package ru.matushov.springcourse.Project2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.matushov.springcourse.Project2Boot.models.Book;


import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    //List<Book> findAll();
    List <Book> findByBookNameStartingWith (String name);
}
