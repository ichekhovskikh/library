package com.haulmont.testtask.model;

import com.haulmont.testtask.dao.*;
import com.haulmont.testtask.entity.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class LibraryModelImpl implements LibraryModel {
    private final PublisherDao publisherDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;

    private List<ModelListener> listeners;

    @Inject
    public LibraryModelImpl(PublisherDao publisherDao, AuthorDao authorDao, GenreDao genreDao, BookDao bookDao) {
        this.publisherDao = publisherDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao =bookDao;
        this.listeners = new ArrayList<>();
    }

    public Publisher getPublisher(long id) {
        return publisherDao.get(id);
    }

    public Author getAuthor(long id) {
        return authorDao.get(id);
    }

    public Genre getGenre(long id) {
        return genreDao.get(id);
    }

    public Book getBook(long id) {
        return bookDao.get(id);
    }

    public List<Publisher> getAllPublishers() {
        return publisherDao.getAll();
    }

    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }

    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    public void update(Author author) {
        authorDao.update(author);
        NotifyListeners(EventType.AUTHORS_CHANGED);
    }

    public void update(Genre genre) {
        genreDao.update(genre);
        NotifyListeners(EventType.GENRES_CHANGED);
    }

    public void update(Book book) {
        bookDao.update(book);
        NotifyListeners(EventType.BOOKS_CHANGED);
    }

    public void add(Author author) {
        authorDao.add(author);
        NotifyListeners(EventType.AUTHORS_CHANGED);
    }

    public void add(Genre genre) {
        genreDao.add(genre);
        NotifyListeners(EventType.GENRES_CHANGED);
    }

    public void add(Book book) {
        bookDao.add(book);
        NotifyListeners(EventType.BOOKS_CHANGED);
    }

    public void remove(Author author) {
        authorDao.remove(author);
        NotifyListeners(EventType.AUTHORS_CHANGED);
    }

    public void remove(Genre genre) {
        genreDao.remove(genre);
        NotifyListeners(EventType.GENRES_CHANGED);
    }

    public void remove(Book book) {
        bookDao.remove(book);
        NotifyListeners(EventType.BOOKS_CHANGED);
    }

    @Override
    public List<Publisher> find(Publisher publisher) {
        return publisherDao.find(publisher);
    }

    @Override
    public List<Author> find(Author author) {
        return authorDao.find(author);
    }

    @Override
    public List<Genre> find(Genre genre) {
        return genreDao.find(genre);
    }

    @Override
    public List<Book> find(Book book) {
        return bookDao.find(book);
    }

    @Override
    public void publisherRollback() {
        publisherDao.rollback();
    }

    @Override
    public void authorRollback() {
        authorDao.rollback();
    }

    @Override
    public void genreRollback() {
        genreDao.rollback();
    }

    @Override
    public void bookRollback() {
        bookDao.rollback();
    }

    @Override
    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ModelListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void NotifyListeners(EventType eventType) {
        listeners.forEach(listener -> listener.modelUpdated(this, eventType));
    }
}
