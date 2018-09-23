package com.haulmont.testtask.ui.book;

import com.haulmont.testtask.entity.*;
import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.ClickableLabel;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

public class SearchBookSubWindow extends AbstractBookSubWindow {
    private Book bookFilter;
    private Map<String, ClickableLabel> filterLabels;
    private BookView parentView;
    private HorizontalLayout filterContainer;

    public SearchBookSubWindow(BookView parentView, String caption, LibraryModel model, LibraryController controller) {
        super(caption, model, controller);
        bookFilter = new Book();
        this.parentView = parentView;
        initFilterContainer();
    }

    public Book getBookFilter() {
        return this.bookFilter;
    }

    public HorizontalLayout getFilterContainer() {
        return filterContainer;
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
        ok.addClickListener(e -> setFilterLabels());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponents(form, ok);
        mainLayout.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        setContent(mainLayout);
    }

    @Override
    protected void fillInformationAboutBook(Book book) {
        book.setAuthor((Author) author.getValue());
        book.setPublisher((Publisher) publisher.getValue());
        book.setGenre((Genre) genre.getValue());
        book.setCity(city.getValue());
        book.setYear(year.getValue().equals("") ? 0 : Integer.valueOf(year.getValue()));
    }

    private void setFilterLabels() {
        filterLabels.values().forEach(this::setInvisibleFilter);
        setFilterWhereNotEmptyFields();
        refreshParentViewGrid();
        close();
    }

    private void setFilterWhereNotEmptyFields() {
        Author authorFilter = (Author) author.getValue();
        if (authorFilter != null && !authorFilter.getName().equals("")) {
            addLabelOnParentView(filterLabels.get("Author"), "Автор", authorFilter.toString());
        }
        Publisher publisherFilter = (Publisher) publisher.getValue();
        if (publisherFilter != null && !publisherFilter.getName().equals("")) {
            addLabelOnParentView(filterLabels.get("Publisher"), "Издатель", publisherFilter.toString());
        }
        Genre genreFilter = (Genre) genre.getValue();
        if (genreFilter != null && !genreFilter.getName().equals("")) {
            addLabelOnParentView(filterLabels.get("Genre"), "Жанр", genreFilter.toString());
        }
        String cityFilter = city.getValue();
        if (cityFilter != null && !cityFilter.equals("")) {
            addLabelOnParentView(filterLabels.get("City"), "Город", cityFilter);
        }
        String yearFilter = year.getValue();
        if (yearFilter != null && !yearFilter.equals("")) {
            addLabelOnParentView(filterLabels.get("Year"), "Год", yearFilter);
        }
    }

    private void refreshParentViewGrid() {
        fillInformationAboutBook(bookFilter);
        bookFilter.setName(parentView.getSearchText());
        List<Book> foundBooks = model.find(bookFilter);
        parentView.refreshGrid(foundBooks);
    }

    private void initFilterContainer() {
        initFilterLabels();
        filterContainer = new HorizontalLayout();
        filterContainer.setSpacing(true);
        filterContainer.addComponents(filterLabels.get("Author"), filterLabels.get("Publisher"),
                filterLabels.get("Genre"), filterLabels.get("City"), filterLabels.get("Year"));
    }

    private void initFilterLabels() {
        filterLabels = newHashMap();
        filterLabels.put("Author", new ClickableLabel());
        filterLabels.put("Publisher", new ClickableLabel());
        filterLabels.put("Genre", new ClickableLabel());
        filterLabels.put("City", new ClickableLabel());
        filterLabels.put("Year", new ClickableLabel());

        Set<String> keys = filterLabels.keySet();
        keys.forEach(key -> {
            ClickableLabel label = filterLabels.get(key);
            label.setVisible(false);
            label.addLayoutClickListener(e -> removeFilter(key, label));
        });
    }

    private void removeFilter(String labelKey, ClickableLabel label) {
        setInvisibleFilter(label);
        clearFilterField(labelKey);
        refreshParentViewGrid();
    }

    private void setInvisibleFilter(ClickableLabel label) {
        label.setVisible(false);
        label.setCaption("");
    }

    private void clearFilterField(String labelKey) {
        switch (labelKey) {
            case "Author":
                author.setValue(null);
                break;
            case "Publisher":
                publisher.setValue(null);
                break;
            case "Genre":
                genre.setValue(null);
                break;
            case "City":
                city.setValue("");
                break;
            case "Year":
                year.setValue("");
                break;
        }
    }

    private void addLabelOnParentView(ClickableLabel label, String labelName, String text) {
        label.setCaption(labelName + ": " + text + " X");
        label.setVisible(true);
    }
}
