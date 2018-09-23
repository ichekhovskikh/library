package com.haulmont.testtask.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.haulmont.testtask.model.*;
import com.haulmont.testtask.module.*;
import com.haulmont.testtask.controller.LibraryController;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

public abstract class AbstractLibraryView extends VerticalLayout implements ModelListener, View {
    protected LibraryModel model;
    protected LibraryController controller;

    protected Grid grid;
    protected TextField search;
    protected Button buttonEdit;
    protected Button buttonAdd;
    protected Button buttonRemove;

    public AbstractLibraryView(String caption) {
        Injector injector = Guice.createInjector(new LibraryModule(), new SessionModule());
        model = injector.getInstance(LibraryModel.class);
        controller = injector.getInstance(LibraryController.class);
        model.addListener(this);

        setSpacing(true);

        addComponent(LibraryComponentFactory.createLibraryHeader(this));
        Component title = LibraryComponentFactory.createLibraryTittle(caption);
        addComponent(title);
        setComponentAlignment(title, Alignment.TOP_CENTER);

        initLibrarySearch();
        initGrid();
        initOperationButtons();
    }

    protected abstract void initLibrarySearch();

    private void initGrid() {
        grid = new Grid();
        grid.setWidth(getWidth(), getWidthUnits());

        addColumnsOnGrid();
        grid.getColumn("Id").setHidden(true);
        grid.addItemClickListener(e -> {
            buttonEdit.setVisible(true);
            buttonRemove.setVisible(true);
        });
        addComponents(grid);
        setComponentAlignment(grid, Alignment.TOP_CENTER);
    }

    protected abstract void addColumnsOnGrid();

    private void initOperationButtons() {
        initRemoveButton();
        initAddButton();
        initEditButton();

        HorizontalLayout operationButtons = new HorizontalLayout();
        operationButtons.setSpacing(true);
        operationButtons.setMargin(true);
        operationButtons.addComponents(buttonEdit, buttonAdd, buttonRemove);
        addComponent(operationButtons);
        setComponentAlignment(operationButtons, Alignment.TOP_RIGHT);
    }

    protected abstract void initEditButton();

    protected abstract void initAddButton();

    protected abstract void initRemoveButton();
}
