package com.haulmont.testtask.module;

import com.google.inject.AbstractModule;
import com.haulmont.testtask.HibernateSessionFactory;
import org.hibernate.Session;

public class SessionModule extends AbstractModule {

    @Override
    public void configure() {
        bind(Session.class).toInstance(HibernateSessionFactory.getSession());
    }
}
