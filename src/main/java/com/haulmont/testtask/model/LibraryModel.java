package com.haulmont.testtask.model;

import com.haulmont.testtask.entity.*;

import java.util.List;

public interface LibraryModel {
    Publisher getPublisher(long id);

    Author getAuthor(long id);

    Genre getGenre(long id);

    Book getBook(long id);

    List<Publisher> getAllPublishers();

    List<Author> getAllAuthors();

    List<Genre> getAllGenres();

    List<Book> getAllBooks();

    void update(Author author);

    void update(Genre genre);

    void update(Book book);

    void add(Author author);

    void add(Genre genre);

    void add(Book book);

    void remove(Author author);

    void remove(Genre genre);

    void remove(Book book);

    List<Publisher> find(Publisher publisher);

    List<Author> find(Author author);

    List<Genre> find(Genre genre);

    List<Book> find(Book book);

    void publisherRollback();

    void authorRollback();

    void genreRollback();

    void bookRollback();

    void addListener(ModelListener listener);

    void removeListener(ModelListener listener);

    void NotifyListeners(EventType eventType);
}
