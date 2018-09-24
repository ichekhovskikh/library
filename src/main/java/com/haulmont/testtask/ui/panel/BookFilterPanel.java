package com.haulmont.testtask.ui.panel;

import com.haulmont.testtask.entity.*;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.AbstractLibraryView;
import com.haulmont.testtask.ui.ClickableLabel;
import com.vaadin.ui.HorizontalLayout;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

public class BookFilterPanel implements FilterPanel<Book> {
    private Book bookFilter;
    private Map<String, ClickableLabel> filterLabels;
    private HorizontalLayout filterContainer;

    private final LibraryModel model;
    private final AbstractLibraryView<Book> contentView;

    public BookFilterPanel(AbstractLibraryView<Book> contentView, LibraryModel model) {
        this.model = model;
        this.contentView = contentView;
        this.bookFilter = new Book();
        initFilterContainer();
    }

    @Override
    public Book getFilter() {
        return this.bookFilter;
    }

    @Override
    public HorizontalLayout getFilterContainer() {
        return filterContainer;
    }

    @Override
    public void setFilters(Book bookFilter) {
        this.bookFilter = bookFilter;
        refreshContentViewGrid();
    }

    @Override
    public void refreshContentViewGrid() {
        bookFilter.setName(contentView.getSearchText());
        List<Book> foundBooks = model.find(bookFilter);
        refreshAllLabelsOnContentView();
        contentView.refreshGrid(foundBooks);
    }

    private void refreshAllLabelsOnContentView() {
        String authorText = (bookFilter.getAuthor() == null) ? "" : bookFilter.getAuthor().toString();
        String genreText = (bookFilter.getGenre() == null) ? "" : bookFilter.getGenre().toString();
        String publisherText = (bookFilter.getPublisher() == null) ? "" : bookFilter.getPublisher().toString();
        String yearText = (bookFilter.getYear() == 0) ? "" : String.valueOf(bookFilter.getYear());

        refreshLabelOnContentView(filterLabels.get("Author"), "Автор", authorText);
        refreshLabelOnContentView(filterLabels.get("Publisher"), "Издатель", publisherText);
        refreshLabelOnContentView(filterLabels.get("Genre"), "Жанр", genreText);
        refreshLabelOnContentView(filterLabels.get("City"), "Город", bookFilter.getCity());
        refreshLabelOnContentView(filterLabels.get("Year"), "Год", yearText);
    }

    private void refreshLabelOnContentView(ClickableLabel label, String labelName, String text) {
        if (text == null || text.equals("")) {
            setInvisibleFilter(label);
        } else {
            label.setCaption(labelName + ": " + text + " X");
            label.setVisible(true);
        }
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
            label.addLayoutClickListener(e -> removeFilter(key));
        });
    }

    private void removeFilter(String labelKey) {
        switch (labelKey) {
            case "Author":
                bookFilter.setAuthor(null);
                break;
            case "Publisher":
                bookFilter.setPublisher(null);
                break;
            case "Genre":
                bookFilter.setGenre(null);
                break;
            case "City":
                bookFilter.setCity("");
                break;
            case "Year":
                bookFilter.setYear(0);
                break;
        }
        refreshContentViewGrid();
    }

    private void setInvisibleFilter(ClickableLabel label) {
        label.setVisible(false);
        label.setCaption("");
    }
}
