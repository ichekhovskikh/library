package com.haulmont.testtask.ui.subwindow.add;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.subwindow.AbstractAuthorSubWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.hibernate.HibernateException;

public class AddAuthorSubWindow extends AbstractAuthorSubWindow {

    public AddAuthorSubWindow(String text, LibraryModel model, LibraryController controller) {
        super(text, model, controller);
    }

    protected void init() {
        FormLayout form = new FormLayout();
        form.setWidth("350");
        form.addStyleName("light");

        name = new TextField("Имя");
        name.setRequired(true);
        form.addComponent(name);

        patronymic = new TextField("Отчество");
        patronymic.setRequired(true);
        form.addComponent(patronymic);

        username = new TextField("Фамилия");
        username.setRequired(true);
        form.addComponent(username);

        Button ok = new Button("OK", FontAwesome.CHECK);
        ok.addStyleName("primary");
        ok.addClickListener(e -> addAuthor());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponents(form, ok);
        mainLayout.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        setContent(mainLayout);
    }

    private void addAuthor() {
        if (hasInvalidFields()) {
            Notification.show("Заполните все поля!");
            return;
        }

        Author author = new Author();
        fillInformationAboutAuthor(author);
        try {
            controller.add(author);
            Notification.show("Добавлено");
            clearWindow();
            close();
        } catch (HibernateException ex) {
            Notification.show(ex.getMessage());
        }
    }
}
