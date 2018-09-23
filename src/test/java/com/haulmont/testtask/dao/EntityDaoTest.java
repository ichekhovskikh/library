package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.entity.Publisher;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityDaoTest {
    protected Session session;

    public EntityDaoTest() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("in-memory-test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        session = entityManager.unwrap(Session.class);
    }

    @After
    public void close(){
        session.close();
    }
}
