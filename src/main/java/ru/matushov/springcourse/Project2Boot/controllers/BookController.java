package ru.matushov.springcourse.Project2Boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.matushov.springcourse.Project2Boot.models.Book;
import ru.matushov.springcourse.Project2Boot.models.Person;
import ru.matushov.springcourse.Project2Boot.services.BooksService;
import ru.matushov.springcourse.Project2Boot.services.PeopleService;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;


    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page",defaultValue = "0") int page,
                        @RequestParam(value = "books_per_page",defaultValue = "999") int booksPerPage,
                        @RequestParam(value = "sort_by_year", defaultValue = "false") boolean sortByYear, Model model) {
        String sortBy= sortByYear?"bookWritten":"bookName";
        model.addAttribute("books", booksService.findAll(page,booksPerPage,sortBy));

        return "books/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findOne(id));

        Optional<Person> bookOwner = booksService.getBookOwner(id);

        if(bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people",peopleService.findALl());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.searchByTitle(query));
        return "books/search";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

}
