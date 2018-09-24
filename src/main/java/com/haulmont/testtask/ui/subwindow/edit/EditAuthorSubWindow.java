package com.haulmont.testtask.ui.subwindow.edit;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.subwindow.AbstractAuthorSubWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.hibernate.HibernateException;

public class EditAuthorSubWindow extends AbstractAuthorSubWindow {
    private long authorId;

    public EditAuthorSubWindow(String text, LibraryModel model, LibraryController controller) {
        super(text, model, controller);
    }

    protected void init() {
        FormLayout form = new FormLayout();
        form.setWidth("350");
        form.addStyleName("light");

        name = new TextField("Имя");
        form.addComponent(name);

        patronymic = new TextField("Отчество");
        form.addComponent(patronymic);

        username = new TextField("Фамилия");
        form.addComponent(username);

        Button ok = new Button("OK", FontAwesome.CHECK);
        ok.addStyleName("primary");
        ok.addClickListener(e -> editAuthor());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponents(form, ok);
        mainLayout.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        setContent(mainLayout);
    }

    public void setSelectedAuthorId(long authorId) {
        this.authorId = authorId;
        Author author = model.getAuthor(authorId);

        name.setValue(author.getName());
        username.setValue(author.getUsername());
        patronymic.setValue(author.getPatronymic());
    }

    private void editAuthor() {
        if (authorId == 0)
            throw new IllegalArgumentException("Author id is not set");
        if (hasInvalidFields()) {
            Notification.show("Заполните все поля!");
            return;
        }

        Author author = model.getAuthor(authorId);
        fillInformationAboutAuthor(author);
        try {
            controller.update(author);
            Notification.show("Обновлено");
            authorId = 0;
            close();
        } catch (HibernateException ex) {
            Notification.show(ex.getMessage());
        }
    }
}
