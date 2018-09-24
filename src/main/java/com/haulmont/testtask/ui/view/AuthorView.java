package com.haulmont.testtask.ui.view;

import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.model.EventType;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.AbstractLibraryView;
import com.haulmont.testtask.ui.LibraryComponentFactory;
import com.haulmont.testtask.ui.panel.AuthorFilterPanel;
import com.haulmont.testtask.ui.panel.FilterPanel;
import com.haulmont.testtask.ui.subwindow.add.AddAuthorSubWindow;
import com.haulmont.testtask.ui.subwindow.edit.EditAuthorSubWindow;
import com.haulmont.testtask.ui.subwindow.search.SearchAuthorSubWindow;
import com.vaadin.data.Item;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.List;

public class AuthorView extends AbstractLibraryView<Author> {

    public AuthorView() {
        super("Авторы");
        refreshGrid();
    }

    @Override
    public void modelUpdated(LibraryModel libraryModel, EventType eventType) {
        if (eventType == EventType.AUTHORS_CHANGED)
            refreshGrid();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    @Override
    public String getSearchText() {
        return search.getValue();
    }

    @Override
    public void refreshGrid() {
        refreshGrid(model.getAllAuthors());
    }

    @Override
    public void refreshGrid(List<Author> authors) {
        grid.getContainerDataSource().removeAllItems();
        authors.forEach(author -> grid.addRow(author.getId(), author.getName(),
                author.getPatronymic(), author.getUsername(), author.getBooks().size()));
        buttonEdit.setVisible(false);
        buttonRemove.setVisible(false);
    }

    @Override
    protected void initLibrarySearch() {
        FilterPanel<Author> filterPanel = new AuthorFilterPanel(this, model);
        SearchAuthorSubWindow searchAuthorSubWindow = new SearchAuthorSubWindow("Расширенный поиск", model, filterPanel);

        Button advancedSearchButton = new Button(FontAwesome.ALIGN_JUSTIFY);
        advancedSearchButton.addClickListener(e -> {
            searchAuthorSubWindow.setInitialValues(filterPanel.getFilter());
            UI.getCurrent().addWindow(searchAuthorSubWindow);
        });
        initSearchField(filterPanel);
        addSearchPanelOnView(advancedSearchButton, filterPanel.getFilterContainer());
    }

    private void initSearchField(FilterPanel<Author> filterPanel) {
        search = new TextField();
        search.setInputPrompt("Поиск по имени...");
        search.addTextChangeListener(e ->  {
            Author authorFilter = filterPanel.getFilter();
            authorFilter.setName(e.getText());
            findAuthor(authorFilter);
        });
    }

    private void findAuthor(Author authorFilter) {
        List<Author> foundAuthors = model.find(authorFilter);
        refreshGrid(foundAuthors);
    }

    @Override
    protected void addColumnsOnGrid() {
        grid.addColumn("Id", Long.class).setHeaderCaption("Идентификатор");
        grid.addColumn("Name", String.class).setHeaderCaption("Имя");
        grid.addColumn("Patronymic", String.class).setHeaderCaption("Отчество");
        grid.addColumn("Username", String.class).setHeaderCaption("Фамилия");
        grid.addColumn("BookCount", Integer.class).setHeaderCaption("Количество книг");
    }

    @Override
    protected void initRemoveButton() {
        buttonRemove = LibraryComponentFactory.createButtonRemove();
        buttonRemove.addClickListener(e -> removeAuthor());
    }

    @Override
    protected void initAddButton() {
        buttonAdd = LibraryComponentFactory.createButtonAdd();
        AddAuthorSubWindow addAuthorSubWindow = new AddAuthorSubWindow(buttonAdd.getCaption(), model, controller);
        buttonAdd.addClickListener(e -> UI.getCurrent().addWindow(addAuthorSubWindow));
    }

    @Override
    protected void initEditButton() {
        buttonEdit = LibraryComponentFactory.createButtonEdit();
        EditAuthorSubWindow editBookSubWindow = new EditAuthorSubWindow(buttonEdit.getCaption(), model, controller);
        buttonEdit.addClickListener(e -> {
            Long id = getSelectedAuthorId();
            editBookSubWindow.setSelectedAuthorId(id);
            UI.getCurrent().addWindow(editBookSubWindow);
        });
    }

    private void removeAuthor() {
        Long id = getSelectedAuthorId();
        Author selectedAuthor = model.getAuthor(id);
        controller.remove(selectedAuthor);
        Notification.show("Удалено");
    }

    private long getSelectedAuthorId() {
        Object selected = grid.getSelectedRow();
        Item selectedItem = grid.getContainerDataSource().getItem(selected);
        return (Long)selectedItem.getItemProperty("Id").getValue();
    }
}
