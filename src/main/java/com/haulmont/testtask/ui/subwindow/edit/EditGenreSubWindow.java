package com.haulmont.testtask.ui.subwindow.edit;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.subwindow.AbstractGenreSubWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.hibernate.HibernateException;

public class EditGenreSubWindow extends AbstractGenreSubWindow {
    private long genreId;

    public EditGenreSubWindow(String text, LibraryModel model, LibraryController controller) {
        super(text, model, controller);
    }

    protected void init() {
        FormLayout form = new FormLayout();
        form.setWidth("350");
        form.addStyleName("light");

        name = new TextField("Название");
        form.addComponent(name);

        Button ok = new Button("OK", FontAwesome.CHECK);
        ok.addStyleName("primary");
        ok.addClickListener(e -> editGenre());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponents(form, ok);
        mainLayout.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        setContent(mainLayout);
    }

    public void setSelectedGenreId(long genreId) {
        this.genreId = genreId;
        Genre genre = model.getGenre(genreId);
        name.setValue(genre.getName());
    }

    private void editGenre() {
        if (genreId == 0)
            throw new IllegalArgumentException("Genre id is not set");
        if (hasInvalidFields()) {
            Notification.show("Заполните все поля!");
            return;
        }

        Genre genre = model.getGenre(genreId);
        fillInformationAboutGenre(genre);
        try {
            controller.update(genre);
            Notification.show("Обновлено");
            genreId = 0;
            close();
        } catch (HibernateException ex) {
            Notification.show(ex.getMessage());
        }
    }
}
