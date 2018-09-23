package com.haulmont.testtask.dao;

import com.haulmont.testtask.SqlQueries;
import com.haulmont.testtask.entity.Author;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.inject.Inject;
import java.util.List;

public class AuthorDao implements EntityDao<Author> {
    private final Session session;

    @Inject
    public AuthorDao(Session session){
        this.session = session;
    }

    @Override
    public List<Author> getAll() {
        return session.createQuery("FROM Author", Author.class)
                .getResultList();
    }

    @Override
    public Author get(long id) {
        return session.get(Author.class, id);
    }

    @Override
    public void update(Author author) {
        session.beginTransaction();
        session.update(author);
        session.getTransaction().commit();
    }

    @Override
    public void add(Author author) {
        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();
    }

    @Override
    public void remove(Author author) {
        session.beginTransaction();
        session.remove(author);
        session.getTransaction().commit();
    }

    @Override
    public List<Author> find(Author authorFilter) {
        NativeQuery<Author> filterQuery = session.createNativeQuery(
                SqlQueries.getAuthorFilterQuery(), Author.class);
        setFilterQueryParameters(filterQuery, authorFilter);
        return filterQuery.getResultList();
    }

    @Override
    public void rollback(){
        session.getTransaction().rollback();
    }

    @Override
    public void closeSession() {
        session.close();
    }

    private void setFilterQueryParameters(NativeQuery<Author> filterQuery, Author author) {
        filterQuery.setParameter("name", getFindByAnyNameRegexp(author.getName()));
        filterQuery.setParameter("patronymic", getFindByAnyNameRegexp(author.getPatronymic()));
        filterQuery.setParameter("username", getFindByAnyNameRegexp(author.getUsername()));
    }

    private String getFindByAnyNameRegexp(String anyName) {
        return "%" + (anyName == null ? "" : anyName) + "%";
    }
}
