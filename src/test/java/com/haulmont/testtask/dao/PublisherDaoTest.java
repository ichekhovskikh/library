package com.haulmont.testtask.dao;
import com.haulmont.testtask.entity.Publisher;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class PublisherDaoTest extends EntityDaoTest {
    private PublisherDao publisherDao;

    @Before
    public void setUp() {
        publisherDao = new PublisherDao(session);
    }

    @Test
    public void get_publisher() {
        Publisher publisher = new Publisher(1, "Москва");
        session.beginTransaction();
        session.save(publisher);
        session.getTransaction().commit();

        assertThat(publisherDao.get(publisher.getId()), is(publisher));
    }

    @Test
    public void get_all_publishers() {
        Publisher publisherMoscow = new Publisher(1, "Москва");
        Publisher publisherPiter = new Publisher(2, "Питер");
        session.beginTransaction();
        session.save(publisherMoscow);
        session.save(publisherPiter);
        session.getTransaction().commit();

        List<Publisher> publishers = publisherDao.getAll();
        assertThat(publishers.size(), is(2));
        assertThat(publishers, contains(publisherMoscow, publisherPiter));
    }

    @Test
    public void update_publisher() {
        Publisher publisher = new Publisher(1, "Москва");
        session.beginTransaction();
        session.save(publisher);
        session.getTransaction().commit();

        String updatedName = "two";
        publisher.setName(updatedName);
        publisherDao.update(publisher);
        Publisher publisherFromBD = session.get(Publisher.class, publisher.getId());
        assertThat(publisherFromBD.getName(), is(updatedName));
    }

    @Test
    public void add_publisher() {
        Publisher publisher = new Publisher(1, "Москва");
        publisherDao.add(publisher);
        Publisher publisherFromBD = session.get(Publisher.class, publisher.getId());
        assertThat(publisherFromBD, is(publisher));
    }

    @Test
    public void remove_publisher() {
        Publisher publisherMoscow = new Publisher(1, "Москва");
        Publisher publisherPiter = new Publisher(2, "Питер");
        session.beginTransaction();
        session.save(publisherMoscow);
        session.save(publisherPiter);
        session.getTransaction().commit();

        publisherDao.remove(publisherMoscow);
        List<Publisher> publishers = session.createQuery("FROM Publisher", Publisher.class)
                .getResultList();
        assertThat(publishers.size(), is(1));
        assertThat(publishers, contains(publisherPiter));
    }

    @Test
    public void find_publishers() {
        Publisher publisherMoscow = new Publisher(1, "Москва");
        Publisher publisherPiter = new Publisher(2, "Питер");
        session.beginTransaction();
        session.save(publisherMoscow);
        session.save(publisherPiter);
        session.getTransaction().commit();

        Publisher publisherFilter = new Publisher("п");
        List<Publisher> publishers = publisherDao.find(publisherFilter);
        assertThat(publishers.size(), is(1));
        assertThat(publishers, contains(publisherPiter));
    }
}
