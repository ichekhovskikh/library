package com.haulmont.testtask.ui.subwindow;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.entity.Publisher;
import com.haulmont.testtask.model.LibraryModel;
import com.vaadin.data.Validator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

public abstract class AbstractBookSubWindow extends Window {
    protected TextField name;
    protected ComboBox author;
    protected ComboBox publisher;
    protected ComboBox genre;
    protected TextField city;
    protected TextField year;

    protected final LibraryController controller;
    protected final LibraryModel model;

    public AbstractBookSubWindow(String text, LibraryModel model, LibraryController controller) {
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

    protected void fillInformationAboutBook(Book book) {
        book.setName(name.getValue());
        book.setAuthor((Author) author.getValue());
        book.setPublisher((Publisher) publisher.getValue());
        book.setGenre((Genre) genre.getValue());
        book.setCity(city.getValue());
        book.setYear(Integer.valueOf(year.getValue()));
    }

    protected boolean hasInvalidFields() {
        try {
            name.validate();
            author.validate();
            publisher.validate();
            genre.validate();
            city.validate();
            year.validate();
            int result = Integer.parseInt(year.getValue());
            return result < 1 || result > 9999;
        } catch (Validator.InvalidValueException | NumberFormatException e) {
            return true;
        }
    }

    protected void clearWindow() {
        name.setValue("");
        author.setValue("");
        publisher.setValue("");
        genre.setValue("");
        city.setValue("");
        year.setValue("");
    }

    protected void setNewValueIfValid(FieldEvents.TextChangeEvent event) {
        TextField field = (TextField) event.getSource();
        String newValue = event.getText();
        if (newValue.equals(""))
            return;
        String oldValue = field.getValue();
        field.setValue(newValue);
        try {
            int result = Integer.parseInt(newValue);
            if (result < 1 || result > 9999)
                field.setValue(oldValue);
        } catch (Exception ex) {
            field.setValue(oldValue);
        }
    }
}
