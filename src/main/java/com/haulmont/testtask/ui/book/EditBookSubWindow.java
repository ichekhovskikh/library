package com.haulmont.testtask.ui.book;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.model.LibraryModel;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.hibernate.HibernateException;

public class EditBookSubWindow extends AbstractBookSubWindow {
    private long bookId;

    public EditBookSubWindow(String text, LibraryModel model, LibraryController controller) {
        super(text, model, controller);
    }

    protected void init() {
        FormLayout form = new FormLayout();
        form.setWidth("350");
        form.addStyleName("light");

        name = new TextField("Название");
        form.addComponent(name);

        author = new ComboBox("Автор");
        author.addItems(model.getAllAuthors());
        form.addComponent(author);

        publisher = new ComboBox("Издатель");
        publisher.addItems(model.getAllPublishers());
        form.addComponent(publisher);

        genre = new ComboBox("Жанр");
        genre.addItems(model.getAllGenres());
        form.addComponent(genre);

        city = new TextField("Город");
        form.addComponent(city);

        year = new TextField("Год");
        year.addTextChangeListener(this::setNewValueIfValid);
        form.addComponent(year);

        Button ok = new Button("OK", FontAwesome.CHECK);
        ok.addStyleName("primary");
        ok.addClickListener(e -> editBook());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponents(form, ok);
        mainLayout.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        setContent(mainLayout);
    }

    public void setSelectedBookId(long bookId) {
        this.bookId = bookId;
        Book book = model.getBook(bookId);

        name.setValue(book.getName());
        author.setValue(book.getAuthor());
        publisher.setValue(book.getPublisher());
        genre.setValue(book.getGenre());
        city.setValue(book.getCity());
        year.setValue(String.valueOf(book.getYear()));
    }

    private void editBook() {
        if (bookId == 0)
            throw new IllegalArgumentException("Book id is not set");
        if (hasInvalidFields()) {
            Notification.show("Заполните все поля!");
            return;
        }

        Book book = model.getBook(bookId);
        fillInformationAboutBook(book);
        try {
            controller.update(book);
            Notification.show("Обновлено");
            bookId = 0;
            close();
        } catch (HibernateException ex) {
            Notification.show(ex.getMessage());
        }
    }
}
