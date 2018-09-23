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

    public GenreView() {
        super("Жанры");
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

    @Override
    protected void initLibrarySearch() {
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

    @Override
    protected void addColumnsOnGrid() {
        grid.addColumn("Id", Long.class).setHeaderCaption("Идентификатор");
        grid.addColumn("Name", String.class).setHeaderCaption("Имя");
        grid.addColumn("BookCount", Integer.class).setHeaderCaption("Количество книг");
    }

    @Override
    protected void initRemoveButton() {
        buttonRemove = LibraryComponentFactory.createButtonRemove();
        buttonRemove.addClickListener(e -> removeGenre());
    }

    @Override
    protected void initAddButton() {
        buttonAdd = LibraryComponentFactory.createButtonAdd();
        AddGenreSubWindow addGenreSubWindow = new AddGenreSubWindow(buttonAdd.getCaption(), model, controller);
        buttonAdd.addClickListener(e -> UI.getCurrent().addWindow(addGenreSubWindow));
    }

    @Override
    protected void initEditButton() {
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
