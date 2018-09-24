package com.haulmont.testtask.ui.subwindow.add;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.subwindow.AbstractGenreSubWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.hibernate.HibernateException;

public class AddGenreSubWindow extends AbstractGenreSubWindow {

    public AddGenreSubWindow(String text, LibraryModel model, LibraryController controller) {
        super(text, model, controller);
    }

    protected void init() {
        FormLayout form = new FormLayout();
        form.setWidth("350");
        form.addStyleName("light");

        name = new TextField("Название");
        name.setRequired(true);
        form.addComponent(name);

        Button ok = new Button("OK", FontAwesome.CHECK);
        ok.addStyleName("primary");
        ok.addClickListener(e -> addGenre());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponents(form, ok);
        mainLayout.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        setContent(mainLayout);
    }

    private void addGenre() {
        if (hasInvalidFields()) {
            Notification.show("Заполните все поля!");
            return;
        }

        Genre genre = new Genre();
        fillInformationAboutGenre(genre);
        try {
            controller.add(genre);
            Notification.show("Добавлено");
            clearWindow();
            close();
        } catch (HibernateException ex) {
            Notification.show(ex.getMessage());
        }
    }
}
