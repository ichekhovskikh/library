package com.haulmont.testtask.dao;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.entity.Publisher;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class BookDaoTest extends EntityDaoTest {
    private BookDao bookDao;

    private Publisher publisher;
    private Author author;
    private Genre genre;

    @Before
    public void setUp() {
        bookDao = new BookDao(session);

        publisher = new Publisher(1, "Москва");
        author = new Author(1, "Александр", "Сергеевич", "Пушкин");
        genre = new Genre(1, "Роман");

        session.beginTransaction();
        session.save(publisher);
        session.save(author);
        session.save(genre);
        session.getTransaction().commit();
    }

    @Test
    public void get_book() {
        Book book = new Book(1, "Евгений Онегин",
                "Кишинев", 1830, publisher, author, genre);
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();

        assertThat(bookDao.get(book.getId()), is(book));
    }

    @Test
    public void get_all_books() {
        Book bookEvgeny = new Book(1, "Евгений Онегин",
                "Кишинев", 1830, publisher, author, genre);
        Book bookBoris = new Book(2, "Борис Годунов",
                "Михайловское", 1825, publisher, author, genre);
        session.beginTransaction();
        session.save(bookEvgeny);
        session.save(bookBoris);
        session.getTransaction().commit();

        List<Book> books = bookDao.getAll();
        assertThat(books.size(), is(2));
        assertThat(books, contains(bookEvgeny, bookBoris));
    }

    @Test
    public void update_book() {
        Book book = new Book(1, "Евгений Онегин",
                "Кишинев", 1830, publisher, author, genre);
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();

        String updatedName = "two";
        book.setName(updatedName);
        bookDao.update(book);
        Book bookFromBD = session.get(Book.class, book.getId());
        assertThat(bookFromBD.getName(), is(updatedName));
    }

    @Test
    public void add_book() {
        Book book = new Book(1, "Евгений Онегин",
                "Кишинев", 1830, publisher, author, genre);
        bookDao.add(book);
        Book bookFromBD = session.get(Book.class, book.getId());
        assertThat(bookFromBD, is(book));
    }

    @Test
    public void remove_book() {
        Book bookEvgeny = new Book(1, "Евгений Онегин",
                "Кишинев", 1830, publisher, author, genre);
        Book bookBoris = new Book(2, "Борис Годунов",
                "Михайловское", 1825, publisher, author, genre);

        session.beginTransaction();
        session.save(bookEvgeny);
        session.save(bookBoris);
        session.getTransaction().commit();

        bookDao.remove(bookEvgeny);
        List<Book> books = session.createQuery("FROM Book", Book.class)
                .getResultList();
        assertThat(books.size(), is(1));
        assertThat(books, contains(bookBoris));
    }

    @Test
    public void find_books() {
        Book bookEvgeny = new Book(1, "Евгений Онегин",
                "Кишинев", 1830, publisher, author, genre);
        Book bookBoris = new Book(2, "Борис Годунов",
                "Михайловское", 1825, publisher, author, genre);

        session.beginTransaction();
        session.save(bookEvgeny);
        session.save(bookBoris);
        session.getTransaction().commit();

        Book bookFilter = new Book();
        bookFilter.setName("ор");
        bookFilter.setPublisher(publisher);
        List<Book> books = bookDao.find(bookFilter);
        assertThat(books.size(), is(1));
        assertThat(books, contains(bookBoris));
    }
}
