package com.haulmont.testtask.dao;
import com.haulmont.testtask.entity.Author;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class AuthorDaoTest extends EntityDaoTest {
    private AuthorDao authorDao;

    @Before
    public void setUp() {
        authorDao = new AuthorDao(session);
    }

    @Test
    public void get_author() {
        Author author = new Author(1, "Александр", "Сергеевич", "Пушкин");
        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();

        assertThat(authorDao.get(author.getId()), is(author));
    }

    @Test
    public void get_all_authors() {
        Author authorPushkin = new Author(1, "Александр", "Сергеевич", "Пушкин");
        Author authorEsenin = new Author(2, "Сергей", "Александрович", "Есенин");
        session.beginTransaction();
        session.save(authorPushkin);
        session.save(authorEsenin);
        session.getTransaction().commit();

        List<Author> authors = authorDao.getAll();
        assertThat(authors.size(), is(2));
        assertThat(authors, contains(authorPushkin, authorEsenin));
    }

    @Test
    public void update_author() {
        Author author = new Author(1, "Александр", "Сергеевич", "Пушкин");
        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();

        String updatedName = "two";
        author.setName(updatedName);
        authorDao.update(author);
        Author authorFromBD = session.get(Author.class, author.getId());
        assertThat(authorFromBD.getName(), is(updatedName));
    }

    @Test
    public void add_author() {
        Author author = new Author(1, "Александр", "Сергеевич", "Пушкин");
        authorDao.add(author);
        Author authorFromBD = session.get(Author.class, author.getId());
        assertThat(authorFromBD, is(author));
    }

    @Test
    public void remove_author() {
        Author authorPushkin = new Author(1, "Александр", "Сергеевич", "Пушкин");
        Author authorEsenin = new Author(2, "Сергей", "Александрович", "Есенин");
        session.beginTransaction();
        session.save(authorPushkin);
        session.save(authorEsenin);
        session.getTransaction().commit();

        authorDao.remove(authorPushkin);
        List<Author> authors = session.createQuery("FROM Author", Author.class)
                .getResultList();
        assertThat(authors.size(), is(1));
        assertThat(authors, contains(authorEsenin));
    }

    @Test
    public void find_authors() {
        Author authorPushkin = new Author(1, "Александр", "Сергеевич", "Пушкин");
        Author authorEsenin = new Author(2, "Сергей", "Александрович", "Есенин");
        session.beginTransaction();
        session.save(authorPushkin);
        session.save(authorEsenin);
        session.getTransaction().commit();

        Author authorFilter = new Author();
        authorFilter.setPatronymic("сан");
        List<Author> authors = authorDao.find(authorFilter);
        assertThat(authors.size(), is(1));
        assertThat(authors, contains(authorEsenin));
    }
}
