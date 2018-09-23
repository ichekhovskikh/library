package com.haulmont.testtask.ui.author;

import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.model.EventType;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.AbstractLibraryView;
import com.haulmont.testtask.ui.LibraryComponentFactory;
import com.vaadin.data.Item;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class AuthorView extends AbstractLibraryView {
    private Grid grid;
    private TextField search;
    private Button buttonEdit;
    private Button buttonAdd;
    private Button buttonRemove;

    public AuthorView() {
        setSpacing(true);

        addComponent(LibraryComponentFactory.createLibraryHeader(this));
        Component title = LibraryComponentFactory.createLibraryTittle("Авторы");
        addComponent(title);
        setComponentAlignment(title, Alignment.TOP_CENTER);

        initLibrarySearch();
        initGrid();
        initOperationButtons();
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

    public String getSearchText() {
        return search.getValue();
    }

    public void refreshGrid() {
        refreshGrid(model.getAllAuthors());
    }

    public void refreshGrid(List<Author> authors) {
        grid.getContainerDataSource().removeAllItems();
        authors.forEach(author -> grid.addRow(author.getId(), author.getName(),
                author.getPatronymic(), author.getUsername(), author.getBooks().size()));
        buttonEdit.setVisible(false);
        buttonRemove.setVisible(false);
    }

    private void initLibrarySearch() {
        Button advancedSearchButton = new Button(FontAwesome.ALIGN_JUSTIFY);
        SearchAuthorSubWindow searchAuthorSubWindow = new SearchAuthorSubWindow(this,
                "Расширенный поиск", model, controller);
        advancedSearchButton.addClickListener(e -> UI.getCurrent().addWindow(searchAuthorSubWindow));

        initSearchField(searchAuthorSubWindow);

        CssLayout filter = new CssLayout();
        filter.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filter.addComponents(search, advancedSearchButton);
        addComponent(filter);
        setComponentAlignment(filter, Alignment.TOP_CENTER);

        Component labelFilters = searchAuthorSubWindow.getFilterContainer();
        addComponent(labelFilters);
        setComponentAlignment(labelFilters, Alignment.TOP_CENTER);
    }

    private void initSearchField(SearchAuthorSubWindow searchAuthorSubWindow) {
        search = new TextField();
        search.setInputPrompt("Поиск по имени...");
        search.addTextChangeListener(e ->  {
            Author authorFilter = searchAuthorSubWindow.getAuthorFilter();
            authorFilter.setName(e.getText());
            findAuthor(authorFilter);
        });
    }

    private void findAuthor(Author authorFilter) {
        List<Author> foundAuthors = model.find(authorFilter);
        refreshGrid(foundAuthors);
    }

    private void initGrid() {
        grid = new Grid();
        grid.setWidth(getWidth(), getWidthUnits());

        addColumnsOnGrid();
        grid.getColumn("Id").setHidden(true);
        grid.addItemClickListener(e -> {
            buttonEdit.setVisible(true);
            buttonRemove.setVisible(true);
        });
        addComponents(grid);
        setComponentAlignment(grid, Alignment.TOP_CENTER);
    }

    private void addColumnsOnGrid() {
        grid.addColumn("Id", Long.class).setHeaderCaption("Идентификатор");
        grid.addColumn("Name", String.class).setHeaderCaption("Имя");
        grid.addColumn("Patronymic", String.class).setHeaderCaption("Отчество");
        grid.addColumn("Username", String.class).setHeaderCaption("Фамилия");
        grid.addColumn("BookCount", Integer.class).setHeaderCaption("Количество книг");
    }

    private void initOperationButtons() {
        initRemoveButton();
        initAddButton();
        initEditButton();

        HorizontalLayout operationButtons = new HorizontalLayout();
        operationButtons.setSpacing(true);
        operationButtons.setMargin(true);
        operationButtons.addComponents(buttonEdit, buttonAdd, buttonRemove);
        addComponent(operationButtons);
        setComponentAlignment(operationButtons, Alignment.TOP_RIGHT);
    }

    private void initRemoveButton() {
        buttonRemove = LibraryComponentFactory.createButtonRemove();
        buttonRemove.addClickListener(e -> removeAuthor());
    }

    private void initAddButton() {
        buttonAdd = LibraryComponentFactory.createButtonAdd();
        AddAuthorSubWindow addAuthorSubWindow = new AddAuthorSubWindow(buttonAdd.getCaption(), model, controller);
        buttonAdd.addClickListener(e -> UI.getCurrent().addWindow(addAuthorSubWindow));
    }

    private void initEditButton() {
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
