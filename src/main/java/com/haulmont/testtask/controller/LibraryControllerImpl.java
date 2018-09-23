package com.haulmont.testtask.controller;

import com.haulmont.testtask.entity.*;
import com.haulmont.testtask.model.LibraryModel;
import org.hibernate.HibernateException;

import javax.inject.Inject;

public class LibraryControllerImpl implements LibraryController {
    private final LibraryModel model;

    @Inject
    public LibraryControllerImpl(LibraryModel model) {
        this.model = model;
    }

    @Override
    public void update(Author author) {
        try {
            model.update(author);
        } catch (Exception e) {
            model.authorRollback();
            throw new HibernateException(e.getMessage());
        }
    }

    @Override
    public void update(Genre genre) {
        try {
            model.update(genre);
        } catch (Exception e) {
            model.genreRollback();
            throw new HibernateException(e.getMessage());
        }
    }

    @Override
    public void update(Book book) {
        try {
            model.update(book);
        } catch (Exception e) {
            model.bookRollback();
            throw new HibernateException(e.getMessage());
        }
    }

    @Override
    public void add(Author author) {
        try {
            model.add(author);
        } catch (Exception e) {
            model.authorRollback();
            throw new HibernateException(e.getMessage());
        }
    }

    @Override
    public void add(Genre genre) {
        try {
            model.add(genre);
        } catch (Exception e) {
            model.genreRollback();
            throw new HibernateException(e.getMessage());
        }
    }

    @Override
    public void add(Book book) {
        try {
            model.add(book);
        } catch (Exception e) {
            model.bookRollback();
            throw new HibernateException(e.getMessage());
        }
    }

    @Override
    public void remove(Author author) {
        try {
            model.remove(author);
        } catch (Exception e) {
            model.authorRollback();
            throw new HibernateException(e.getMessage());
        }
    }

    @Override
    public void remove(Genre genre) {
        try {
            model.remove(genre);
        } catch (Exception e) {
            model.genreRollback();
            throw new HibernateException(e.getMessage());
        }
    }

    @Override
    public void remove(Book book) {
        try {
            model.remove(book);
        } catch (Exception e) {
            model.bookRollback();
            throw new HibernateException(e.getMessage());
        }
    }
}
