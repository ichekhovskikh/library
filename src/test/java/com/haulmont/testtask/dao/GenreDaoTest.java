package com.haulmont.testtask.dao;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.entity.Publisher;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class GenreDaoTest extends EntityDaoTest {
    private GenreDao genreDao;

    @Before
    public void setUp() {
        genreDao = new GenreDao(session);
    }

    @Test
    public void get_genre() {
        Genre genre = new Genre(1, "Рассказ");
        session.beginTransaction();
        session.save(genre);
        session.getTransaction().commit();

        assertThat(genreDao.get(genre.getId()), is(genre));
    }

    @Test
    public void get_all_genres() {
        Genre genreStory = new Genre(1, "Рассказ");
        Genre genreNovel = new Genre(2, "Роман");
        session.beginTransaction();
        session.save(genreStory);
        session.save(genreNovel);
        session.getTransaction().commit();

        List<Genre> genres = genreDao.getAll();
        assertThat(genres.size(), is(2));
        assertThat(genres, contains(genreStory, genreNovel));
    }

    @Test
    public void update_genre() {
        Genre genre = new Genre(1, "Рассказ");
        session.beginTransaction();
        session.save(genre);
        session.getTransaction().commit();

        String updatedName = "two";
        genre.setName(updatedName);
        genreDao.update(genre);
        Genre genreFromBD = session.get(Genre.class, genre.getId());
        assertThat(genreFromBD.getName(), is(updatedName));
    }

    @Test
    public void add_genre() {
        Genre genre = new Genre(1, "Рассказ");
        genreDao.add(genre);
        Genre genreFromBD = session.get(Genre.class, genre.getId());
        assertThat(genreFromBD, is(genre));
    }

    @Test
    public void remove_genre() {
        Genre genreStory = new Genre(1, "Рассказ");
        Genre genreNovel = new Genre(2, "Роман");
        session.beginTransaction();
        session.save(genreStory);
        session.save(genreNovel);
        session.getTransaction().commit();

        genreDao.remove(genreStory);
        List<Genre> genres = session.createQuery("FROM Genre", Genre.class)
                .getResultList();
        assertThat(genres.size(), is(1));
        assertThat(genres, contains(genreNovel));
    }

    @Test
    public void find_genres() {
        Genre genreStory = new Genre(1, "Рассказ");
        Genre genreNovel = new Genre(2, "Роман");
        session.beginTransaction();
        session.save(genreStory);
        session.save(genreNovel);
        session.getTransaction().commit();

        Genre genreFilter = new Genre("ро");
        List<Genre> genres = genreDao.find(genreFilter);
        assertThat(genres.size(), is(1));
        assertThat(genres, contains(genreNovel));
    }
}
