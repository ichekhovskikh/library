package com.haulmont.testtask.dao;

import com.haulmont.testtask.SqlQueries;
import com.haulmont.testtask.entity.Publisher;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.inject.Inject;
import java.util.List;

public class PublisherDao implements EntityDao<Publisher> {
    private final Session session;

    @Inject
    public PublisherDao(Session session){
        this.session = session;
    }

    @Override
    public List<Publisher> getAll() {
        return session.createQuery("FROM Publisher", Publisher.class)
                .getResultList();
    }

    @Override
    public Publisher get(long id) {
        return session.get(Publisher.class, id);
    }

    @Override
    public void update(Publisher publisher) {
        session.beginTransaction();
        session.update(publisher);
        session.getTransaction().commit();
    }

    @Override
    public void add(Publisher publisher) {
        session.beginTransaction();
        session.save(publisher);
        session.getTransaction().commit();
    }

    @Override
    public void remove(Publisher publisher) {
        session.beginTransaction();
        session.remove(publisher);
        session.getTransaction().commit();
    }

    @Override
    public List<Publisher> find(Publisher publisherFilter) {
        NativeQuery<Publisher> filterQuery = session.createNativeQuery(
                SqlQueries.getPublisherFilterQuery(), Publisher.class);
        filterQuery.setParameter("name", getFindByNameRegexp(publisherFilter.getName()));
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
