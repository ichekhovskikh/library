package com.haulmont.testtask.ui.subwindow.search;

import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.panel.FilterPanel;
import com.haulmont.testtask.ui.subwindow.AbstractAuthorSubWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

public class SearchAuthorSubWindow extends AbstractAuthorSubWindow {
    private FilterPanel<Author> filterPanel;

    public SearchAuthorSubWindow(String caption, LibraryModel model, FilterPanel<Author> filterPanel) {
        super(caption, model, null);
        this.filterPanel = filterPanel;
    }

    public void setInitialValues(Author authorFilter) {
        String patronymicText = (authorFilter.getPatronymic() == null) ? "" : authorFilter.getPatronymic();
        String usernameText = (authorFilter.getUsername() == null) ? "" : authorFilter.getUsername();
        patronymic.setValue(patronymicText);
        username.setValue(usernameText);
    }

    protected void init() {
        FormLayout form = new FormLayout();
        form.setWidth("350");
        form.addStyleName("light");

        patronymic = new TextField("Отчество");
        form.addComponent(patronymic);

        username = new TextField("Фамилия");
        form.addComponent(username);

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
        Author authorFilter = new Author("", patronymic.getValue(), username.getValue());
        filterPanel.setFilters(authorFilter);
        close();
    }
}
