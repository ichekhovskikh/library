package com.haulmont.testtask.ui.book;

import com.haulmont.testtask.entity.*;
import com.haulmont.testtask.model.*;
import com.haulmont.testtask.ui.AbstractLibraryView;
import com.haulmont.testtask.ui.LibraryComponentFactory;
import com.vaadin.data.Item;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class BookView extends AbstractLibraryView {

    public BookView() {
        super("Книги");
        refreshGrid();
    }

    @Override
    public void modelUpdated(LibraryModel libraryModel, EventType eventType) {
        if (eventType == EventType.BOOKS_CHANGED)
            refreshGrid();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    public String getSearchText() {
        return search.getValue();
    }

    public void refreshGrid() {
        refreshGrid(model.getAllBooks());
    }

    public void refreshGrid(List<Book> books) {
        grid.getContainerDataSource().removeAllItems();
        books.forEach(book -> grid.addRow(book.getId(), book.getName(), book.getAuthor(),
                book.getPublisher(), book.getGenre(), book.getCity(), book.getYear()));
        buttonEdit.setVisible(false);
        buttonRemove.setVisible(false);
    }

    @Override
    protected void initLibrarySearch() {
        Button advancedSearchButton = new Button(FontAwesome.ALIGN_JUSTIFY);
        SearchBookSubWindow searchBookSubWindow = new SearchBookSubWindow(this,
                "Расширенный поиск", model, controller);
        advancedSearchButton.addClickListener(e -> UI.getCurrent().addWindow(searchBookSubWindow));

        initSearchField(searchBookSubWindow);

        CssLayout filter = new CssLayout();
        filter.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filter.addComponents(search, advancedSearchButton);
        addComponent(filter);
        setComponentAlignment(filter, Alignment.TOP_CENTER);

        Component labelFilters = searchBookSubWindow.getFilterContainer();
        addComponent(labelFilters);
        setComponentAlignment(labelFilters, Alignment.TOP_CENTER);
    }

    private void initSearchField(SearchBookSubWindow searchBookSubWindow) {
        search = new TextField();
        search.setInputPrompt("Поиск по названию...");
        search.addTextChangeListener(e ->  {
            Book bookFilter = searchBookSubWindow.getBookFilter();
            bookFilter.setName(e.getText());
            findBook(bookFilter);
        });
    }

    private void findBook(Book bookFilter) {
        List<Book> foundBooks = model.find(bookFilter);
        refreshGrid(foundBooks);
    }

    @Override
    protected void addColumnsOnGrid() {
        grid.addColumn("Id", Long.class).setHeaderCaption("Идентификатор");
        grid.addColumn("Name", String.class).setHeaderCaption("Название");
        grid.addColumn("Author", Author.class).setHeaderCaption("Автор");
        grid.addColumn("Publisher", Publisher.class).setHeaderCaption("Издатель");
        grid.addColumn("Genre", Genre.class).setHeaderCaption("Жанр");
        grid.addColumn("City", String.class).setHeaderCaption("Город");
        grid.addColumn("Year", Integer.class).setHeaderCaption("Год");
    }

    @Override
    protected void initRemoveButton() {
        buttonRemove = LibraryComponentFactory.createButtonRemove();
        buttonRemove.addClickListener(e -> removeBook());
    }

    @Override
    protected void initAddButton() {
        buttonAdd = LibraryComponentFactory.createButtonAdd();
        AddBookSubWindow addBookSubWindow = new AddBookSubWindow(buttonAdd.getCaption(), model, controller);
        buttonAdd.addClickListener(e -> UI.getCurrent().addWindow(addBookSubWindow));
    }

    @Override
    protected void initEditButton() {
        buttonEdit = LibraryComponentFactory.createButtonEdit();
        EditBookSubWindow editBookSubWindow = new EditBookSubWindow(buttonEdit.getCaption(), model, controller);
        buttonEdit.addClickListener(e -> {
            Long id = getSelectedBookId();
            editBookSubWindow.setSelectedBookId(id);
            UI.getCurrent().addWindow(editBookSubWindow);
        });
    }

    private void removeBook() {
        Long id = getSelectedBookId();
        Book selectedBook = model.getBook(id);
        controller.remove(selectedBook);
        Notification.show("Удалено");
    }

    private long getSelectedBookId() {
        Object selected = grid.getSelectedRow();
        Item selectedItem = grid.getContainerDataSource().getItem(selected);
        return (Long)selectedItem.getItemProperty("Id").getValue();
    }
}
