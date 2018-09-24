package com.haulmont.testtask.ui.subwindow.add;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.subwindow.AbstractBookSubWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.hibernate.HibernateException;

public class AddBookSubWindow extends AbstractBookSubWindow {

    public AddBookSubWindow(String text, LibraryModel model, LibraryController controller) {
        super(text, model, controller);
    }

    protected void init() {
        FormLayout form = new FormLayout();
        form.setWidth("350");
        form.addStyleName("light");

        name = new TextField("Название");
        name.setRequired(true);
        form.addComponent(name);

        author = new ComboBox("Автор");
        author.setRequired(true);
        author.addItems(model.getAllAuthors());
        form.addComponent(author);

        publisher = new ComboBox("Издатель");
        publisher.setRequired(true);
        publisher.addItems(model.getAllPublishers());
        form.addComponent(publisher);

        genre = new ComboBox("Жанр");
        genre.setRequired(true);
        genre.addItems(model.getAllGenres());
        form.addComponent(genre);

        city = new TextField("Город");
        city.setRequired(true);
        form.addComponent(city);

        year = new TextField("Год");
        year.setRequired(true);
        year.addTextChangeListener(this::setNewValueIfValid);
        form.addComponent(year);

        Button ok = new Button("OK", FontAwesome.CHECK);
        ok.addStyleName("primary");
        ok.addClickListener(e -> addBook());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponents(form, ok);
        mainLayout.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        setContent(mainLayout);
    }

    private void addBook() {
        if (hasInvalidFields()) {
            Notification.show("Заполните все поля!");
            return;
        }

        Book book = new Book();
        fillInformationAboutBook(book);
        try {
            controller.add(book);
            Notification.show("Добавлено");
            clearWindow();
            close();
        } catch (HibernateException ex) {
            Notification.show(ex.getMessage());
        }
    }
}
