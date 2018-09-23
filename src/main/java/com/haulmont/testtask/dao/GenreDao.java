package com.haulmont.testtask.dao;

import com.haulmont.testtask.SqlQueries;
import com.haulmont.testtask.entity.Genre;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.inject.Inject;
import java.util.List;

public class GenreDao implements EntityDao<Genre> {
    private final Session session;

    @Inject
    public GenreDao(Session session){
        this.session = session;
    }

    @Override
    public List<Genre> getAll() {
        return session.createQuery("FROM Genre", Genre.class)
                .getResultList();
    }

    @Override
    public Genre get(long id) {
        return session.get(Genre.class, id);
    }

    @Override
    public void update(Genre genre) {
        session.beginTransaction();
        session.update(genre);
        session.getTransaction().commit();
    }

    @Override
    public void add(Genre genre) {
        session.beginTransaction();
        session.save(genre);
        session.getTransaction().commit();
    }

    @Override
    public void remove(Genre genre) {
        session.beginTransaction();
        session.remove(genre);
        session.getTransaction().commit();
    }

    @Override
    public List<Genre> find(Genre genreFilter) {
        NativeQuery<Genre> filterQuery = session.createNativeQuery(
                SqlQueries.getGenreFilterQuery(), Genre.class);
        filterQuery.setParameter("name", getFindByNameRegexp(genreFilter.getName()));
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

    private String getFindByNameRegexp(String anyName) {
        return "%" + (anyName == null ? "" : anyName) + "%";
    }
}
