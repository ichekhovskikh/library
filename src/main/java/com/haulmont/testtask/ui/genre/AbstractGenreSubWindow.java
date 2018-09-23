package com.haulmont.testtask.ui.genre;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.model.LibraryModel;
import com.vaadin.data.Validator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public abstract class AbstractGenreSubWindow extends Window {
    protected TextField name;

    protected final LibraryController controller;
    protected final LibraryModel model;

    public AbstractGenreSubWindow(String text, LibraryModel model, LibraryController controller) {
        super(text);
        center();
        setModal(true);
        setResizable(false);
        setDraggable(false);

        this.controller = controller;
        this.model = model;
        init();
    }

    protected abstract void init();

    protected void fillInformationAboutGenre(Genre genre) {
        genre.setName(name.getValue());
    }

    protected boolean hasInvalidFields() {
        try {
            name.validate();
            return false;
        } catch (Validator.InvalidValueException e) {
            return true;
        }
    }

    protected void clearWindow() {
        name.setValue("");
    }
}
