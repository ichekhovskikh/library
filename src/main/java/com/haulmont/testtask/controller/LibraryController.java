package com.haulmont.testtask.controller;

import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Genre;

public interface LibraryController {
    void update(Author author);

    void update(Genre genre);

    void update(Book book);

    void add(Author author);

    void add(Genre genre);

    void add(Book book);

    void remove(Author author);

    void remove(Genre genre);

    void remove(Book book);
}
