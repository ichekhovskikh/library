package com.haulmont.testtask.ui.subwindow.search;

import com.haulmont.testtask.entity.*;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.panel.FilterPanel;
import com.haulmont.testtask.ui.subwindow.AbstractBookSubWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

public class SearchBookSubWindow extends AbstractBookSubWindow {
    private FilterPanel<Book> filterPanel;

    public SearchBookSubWindow(String caption, LibraryModel model, FilterPanel<Book> filterPanel) {
        super(caption, model, null);
        this.filterPanel = filterPanel;
    }

    public void setInitialValues(Book bookFilter) {
        author.setValue(bookFilter.getAuthor());
        publisher.setValue(bookFilter.getPublisher());
        genre.setValue(bookFilter.getGenre());
        String cityText = (bookFilter.getCity() == null) ? "" : bookFilter.getCity();
        city.setValue(cityText);
        String yearText = (bookFilter.getYear() == 0) ? "" : String.valueOf(bookFilter.getYear());
        year.setValue(yearText);
    }

    protected void init() {
        FormLayout form = new FormLayout();
        form.setWidth("350");
        form.addStyleName("light");

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

        Button ok = new Button("ПОИСК", FontAwesome.SEARCH);
        ok.addStyleName("primary");
        ok.addClickListener(e -> setFilters());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponents(form, ok);
        mainLayout.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        setContent(mainLayout);
    }

    private void setFilters() {
        int yearInt = year.getValue().equals("") ? 0 : Integer.parseInt(year.getValue());
        Book bookFilter = new Book(
                "",
                city.getValue(),
                yearInt,
                (Publisher) publisher.getValue(),
                (Author) author.getValue(),
                (Genre) genre.getValue());
        filterPanel.setFilters(bookFilter);
        close();
    }
}
