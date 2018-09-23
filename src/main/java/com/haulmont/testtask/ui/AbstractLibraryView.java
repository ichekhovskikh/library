package com.haulmont.testtask.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.haulmont.testtask.model.*;
import com.haulmont.testtask.module.*;
import com.haulmont.testtask.controller.LibraryController;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractLibraryView extends VerticalLayout implements ModelListener, View {
    protected LibraryModel model;
    protected LibraryController controller;

    public AbstractLibraryView() {
        Injector injector = Guice.createInjector(new LibraryModule(), new SessionModule());
        model = injector.getInstance(LibraryModel.class);
        controller = injector.getInstance(LibraryController.class);
        model.addListener(this);
    }
}
