package com.haulmont.testtask.dao;

import com.haulmont.testtask.SqlQueries;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Entity;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.inject.Inject;
import java.util.List;

public class BookDao implements EntityDao<Book> {
    private final Session session;

    @Inject
    public BookDao(Session session) {
        this.session = session;
    }

    @Override
    public List<Book> getAll() {
        return session.createQuery("FROM Book", Book.class)
                .getResultList();
    }

    @Override
    public Book get(long id) {
        return session.get(Book.class, id);
    }

    @Override
    public void update(Book book) {
        session.beginTransaction();
        session.update(book);
        session.getTransaction().commit();
    }

    @Override
    public void add(Book book) {
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
    }

    @Override
    public void remove(Book book) {
        session.beginTransaction();
        session.remove(book);
        session.getTransaction().commit();
    }

    @Override
    public List<Book> find(Book bookFilter) {
        NativeQuery<Book> filterQuery = session.createNativeQuery(
                SqlQueries.getBookFilterQuery(), Book.class);
        setFilterQueryParameters(filterQuery, bookFilter);
        return filterQuery.getResultList();
    }

    @Override
    public void rollback() {
        session.getTransaction().rollback();
    }

    @Override
    public void closeSession() {
        session.close();
    }

    private void setFilterQueryParameters(NativeQuery<Book> filterQuery, Book book) {
        final String regexpForCity = "%" + (book.getCity() == null ? "" : book.getCity()) + "%";
        filterQuery.setParameter("city", regexpForCity);

        final String regexpForYear = "%" + (book.getYear() == 0 ? "" : book.getYear()) + "%";
        filterQuery.setParameter("year", regexpForYear);

        filterQuery.setParameter("book_name", getFindByNameRegexp(book));
        filterQuery.setParameter("publisher_id", getIdOrZeroIfNull(book.getPublisher()));
        filterQuery.setParameter("author_id", getIdOrZeroIfNull(book.getAuthor()));
        filterQuery.setParameter("genre_id", getIdOrZeroIfNull(book.getGenre()));
    }

    private long getIdOrZeroIfNull(Entity entity) {
        return entity == null ? 0 : entity.getId();
    }

    private String getFindByNameRegexp(Entity entity) {
        String name = (entity == null) ? "" : entity.getName();
        return "%" + (name == null ? "" : name) + "%";
    }
}
