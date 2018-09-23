package com.haulmont.testtask.ui.author;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.model.LibraryModel;
import com.vaadin.data.Validator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public abstract class AbstractAuthorSubWindow extends Window {
    protected TextField name;
    protected TextField patronymic;
    protected TextField username;

    protected final LibraryController controller;
    protected final LibraryModel model;

    public AbstractAuthorSubWindow(String text, LibraryModel model, LibraryController controller) {
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

    protected void fillInformationAboutAuthor(Author author) {
        author.setName(name.getValue());
        author.setPatronymic(patronymic.getValue());
        author.setUsername(username.getValue());
    }

    protected boolean hasInvalidFields() {
        try {
            name.validate();
            patronymic.validate();
            username.validate();
            return false;
        } catch (Validator.InvalidValueException e) {
            return true;
        }
    }

    protected void clearWindow() {
        name.setValue("");
        patronymic.setValue("");
        username.setValue("");
    }
}
