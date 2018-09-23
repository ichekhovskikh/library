package com.haulmont.testtask.ui.genre;

import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.model.EventType;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.AbstractLibraryView;
import com.haulmont.testtask.ui.LibraryComponentFactory;
import com.vaadin.data.Item;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

public class GenreView extends AbstractLibraryView {
    private Grid grid;
    private TextField search;
    private Button buttonEdit;
    private Button buttonAdd;
    private Button buttonRemove;

    public GenreView() {
        setSpacing(true);

        addComponent(LibraryComponentFactory.createLibraryHeader(this));
        Component title = LibraryComponentFactory.createLibraryTittle("Жанры");
        addComponent(title);
        setComponentAlignment(title, Alignment.TOP_CENTER);

        initLibrarySearch();
        initGrid();
        initOperationButtons();
        refreshGrid();
    }

    @Override
    public void modelUpdated(LibraryModel libraryModel, EventType eventType) {
        if (eventType == EventType.GENRES_CHANGED)
            refreshGrid();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    public void refreshGrid() {
        refreshGrid(model.getAllGenres());
    }

    public void refreshGrid(List<Genre> genres) {
        grid.getContainerDataSource().removeAllItems();
        genres.forEach(genre -> grid.addRow(genre.getId(), genre.getName(), genre.getBooks().size()));
        buttonEdit.setVisible(false);
        buttonRemove.setVisible(false);
    }

    private void initLibrarySearch() {
        search = new TextField();
        search.setInputPrompt("Поиск по названию...");
        search.addTextChangeListener(e ->  {
            Genre genreFilter = new Genre();
            genreFilter.setName(e.getText());
            findGenre(genreFilter);
        });
        addComponent(search);
        setComponentAlignment(search, Alignment.TOP_CENTER);
    }

    private void findGenre(Genre genreFilter) {
        List<Genre> foundGenres = model.find(genreFilter);
        refreshGrid(foundGenres);
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
        buttonRemove.addClickListener(e -> removeGenre());
    }

    private void initAddButton() {
        buttonAdd = LibraryComponentFactory.createButtonAdd();
        AddGenreSubWindow addGenreSubWindow = new AddGenreSubWindow(buttonAdd.getCaption(), model, controller);
        buttonAdd.addClickListener(e -> UI.getCurrent().addWindow(addGenreSubWindow));
    }

    private void initEditButton() {
        buttonEdit = LibraryComponentFactory.createButtonEdit();
        EditGenreSubWindow editGenreSubWindow = new EditGenreSubWindow(buttonEdit.getCaption(), model, controller);
        buttonEdit.addClickListener(e -> {
            Long id = getSelectedGenreId();
            editGenreSubWindow.setSelectedGenreId(id);
            UI.getCurrent().addWindow(editGenreSubWindow);
        });
    }

    private void removeGenre() {
        Long id = getSelectedGenreId();
        Genre selectedGenre = model.getGenre(id);
        controller.remove(selectedGenre);
        Notification.show("Удалено");
    }

    private long getSelectedGenreId() {
        Object selected = grid.getSelectedRow();
        Item selectedItem = grid.getContainerDataSource().getItem(selected);
        return (Long)selectedItem.getItemProperty("Id").getValue();
    }
}
