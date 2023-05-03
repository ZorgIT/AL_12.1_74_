package ru.matushov.springcourse.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author Stepan Matushov
 */

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name ="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name ="user_name")
    @NotEmpty(message="Имя не должно быть пустым")
    @Size(min = 2,max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    private String user_name;

    @Column(name = "user_birthday")
    @Min(value =1900, message = "Год рождения должен быть больше, чем 1900")
    private int user_birthday;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    //Конструктор по умолчанию, нужен для корректного функционирования Spring
    public Person() {

    }

    public Person(String user_name, int user_birthday) {
        this.user_name = user_name;
        this.user_birthday = user_birthday;

    }

    public int getUser_id() {
        return user_id;
    }

    //TODO уточнить, нужно ли существование сеттера для ID
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(int user_birthday) {
        this.user_birthday = user_birthday;
    }


    @Override
    public String toString() {
        return "Person{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_birthday=" + user_birthday +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return user_id == person.user_id && user_birthday == person.user_birthday && Objects.equals(user_name, person.user_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, user_name, user_birthday);
    }
}
