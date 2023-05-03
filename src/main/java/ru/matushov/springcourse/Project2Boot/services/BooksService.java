package ru.matushov.springcourse.Project2Boot.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.matushov.springcourse.Project2Boot.models.Book;
import ru.matushov.springcourse.Project2Boot.models.Person;
import ru.matushov.springcourse.Project2Boot.repositories.BooksRepository;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleService peopleService;
    private  final EntityManager entityManager;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleService peopleService, EntityManager entityManager) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
        this.entityManager = entityManager;
    }


    public List<Book> findAll() {
        return booksRepository.findAll();
    }
    public List<Book> findAll(int page, int itemsPerPage, String fieldName) {
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage,Sort.by(fieldName))).getContent();
    }
//,Sort.by(fieldName)

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List <Book> searchByTitle(String query){
        return  booksRepository.findByBookNameStartingWith(query);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        // добавляем по сути новую книгу (которая не находится в Persistence context), поэтому нужен save()
        updatedBook.setBook_id(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner()); // чтобы не терялась связь при обновлении

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Optional<Person> getBookOwner(int id){
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class,id);
        Person person = book.getOwner();
        return  Optional.ofNullable(person);
    }


    // Освбождает книгу (этот метод вызывается, когда человек возвращает книгу в библиотеку)
    @Transactional
    public void release(int id) {
        //jdbcTemplate.update("UPDATE Book SET user_id=NULL WHERE book_id=?", id);
//        Session session = entityManager.unwrap(Session.class);
//        Book book = session.get(Book.class,id);
//        book.setOwner(null);
//        booksRepository.save(book);

        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }

    // Назначает книгу человеку (этот метод вызывается, когда человек забирает книгу из библиотеки)
    @Transactional
    public void assign(int id, Person selectedPerson) {
//        Session session = entityManager.unwrap(Session.class);
//        Book book = session.get(Book.class,id);
//        book.setOwner(selectedPerson);
//        booksRepository.save(book);

        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date()); // текущее время
                }
        );
    }


}
