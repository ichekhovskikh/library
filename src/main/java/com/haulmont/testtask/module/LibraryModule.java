package com.haulmont.testtask.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.haulmont.testtask.controller.*;
import com.haulmont.testtask.dao.*;
import com.haulmont.testtask.entity.*;
import com.haulmont.testtask.model.*;

public class LibraryModule extends AbstractModule {

    @Override
    public void configure() {
        bind(new TypeLiteral<EntityDao<Publisher>>() {}).to(PublisherDao.class).in(Singleton.class);
        bind(new TypeLiteral<EntityDao<Genre>>() {}).to(GenreDao.class).in(Singleton.class);
        bind(new TypeLiteral<EntityDao<Author>>() {}).to(AuthorDao.class).in(Singleton.class);
        bind(new TypeLiteral<EntityDao<Book>>() {}).to(BookDao.class).in(Singleton.class);
        bind(LibraryModel.class).to(LibraryModelImpl.class).in(Singleton.class);
        bind(LibraryController.class).to(LibraryControllerImpl.class).in(Singleton.class);
    }
}
