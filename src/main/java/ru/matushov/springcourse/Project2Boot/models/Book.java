package ru.matushov.springcourse.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    //Генерируется автоматически, проверка не требуется

    @Id
    @Column(name ="book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private Person owner;

    @Transient
    private boolean expired;

    @Column(name = "book_name")
    @NotEmpty(message="Название книги не должно быть пустым")
    @Size(min = 2,max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
    private String bookName;

    @Column(name="book_author")
    @NotEmpty(message="Автор не должен быть пустым")
    @Size(min = 2,max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    private String bookAuthor;
    @Column(name="book_written")
    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    private int bookWritten;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;


    //Конструктор по умолчанию, нужен для корректного функционирования Spring
    public Book(){

    }

    public Book(String bookName, String author, int written) {
        this.bookName = bookName;
        this.bookAuthor =author;
        this.bookWritten =written;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String book_name) {
        this.bookName = book_name;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String book_author) {
        this.bookAuthor = book_author;
    }

    public int getBookWritten() {
        return bookWritten;
    }

    public void setBookWritten(int book_written) {
        this.bookWritten = book_written;
    }

    public boolean isExpired() {
        if (this.takenAt != null) {
            if (this.takenAt.before(new Date())) setExpired(true);
        }
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", owner=" + owner +
                ", book_name='" + bookName + '\'' +
                ", book_author='" + bookAuthor + '\'' +
                ", book_written=" + bookWritten +
                '}';
    }
}
