package com.haulmont.testtask.module;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.dao.*;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.module.LibraryModule;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(MockitoJUnitRunner.class)
public class LibraryModuleTest {
    private Injector injector;
    @Mock
    private Session session;

    @Before
    public void setUp() {
        injector = Guice.createInjector(new LibraryModule(), new AbstractModule() {
            @Override
            protected void configure() {
                bind(Session.class).toInstance(session);
            }
        });
    }

    @Test
    public void publisherDao_created() {
        PublisherDao publisherDao = injector.getInstance(PublisherDao.class);
        assertThat(publisherDao, notNullValue());
    }

    @Test
    public void genreDao_created() {
        GenreDao genreDao = injector.getInstance(GenreDao.class);
        assertThat(genreDao, notNullValue());
    }

    @Test
    public void authorDao_created() {
        AuthorDao authorDao = injector.getInstance(AuthorDao.class);
        assertThat(authorDao, notNullValue());
    }

    @Test
    public void bookDao_created() {
        BookDao bookDao = injector.getInstance(BookDao.class);
        assertThat(bookDao, notNullValue());
    }

    @Test
    public void model_created() {
        LibraryModel model = injector.getInstance(LibraryModel.class);
        assertThat(model, notNullValue());
    }

    @Test
    public void controller_created() {
        LibraryController controller = injector.getInstance(LibraryController.class);
        assertThat(controller, notNullValue());
    }
}
