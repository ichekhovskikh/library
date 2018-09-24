package com.haulmont.testtask.ui.panel;

import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.AbstractLibraryView;
import com.haulmont.testtask.ui.ClickableLabel;
import com.vaadin.ui.HorizontalLayout;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

public class AuthorFilterPanel implements FilterPanel<Author> {
    private Author authorFilter;
    private Map<String, ClickableLabel> filterLabels;
    private HorizontalLayout filterContainer;

    private final LibraryModel model;
    private final AbstractLibraryView<Author> contentView;

    public AuthorFilterPanel(AbstractLibraryView<Author> contentView, LibraryModel model) {
        this.model = model;
        this.contentView = contentView;
        this.authorFilter = new Author();
        initFilterContainer();
    }

    @Override
    public Author getFilter() {
        return this.authorFilter;
    }

    @Override
    public HorizontalLayout getFilterContainer() {
        return filterContainer;
    }

    @Override
    public void setFilters(Author authorFilter) {
        this.authorFilter = authorFilter;
        refreshContentViewGrid();
    }

    @Override
    public void refreshContentViewGrid() {
        authorFilter.setName(contentView.getSearchText());
        List<Author> foundAuthors = model.find(authorFilter);
        refreshAllLabelsOnContentView();
        contentView.refreshGrid(foundAuthors);
    }

    private void refreshAllLabelsOnContentView() {
        refreshLabelOnContentView(filterLabels.get("Patronymic"), "Отчество", authorFilter.getPatronymic());
        refreshLabelOnContentView(filterLabels.get("Username"), "Фамилия", authorFilter.getUsername());
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
        filterContainer.addComponents(filterLabels.get("Patronymic"), filterLabels.get("Username"));
    }

    private void initFilterLabels() {
        filterLabels = newHashMap();
        filterLabels.put("Patronymic", new ClickableLabel());
        filterLabels.put("Username", new ClickableLabel());

        Set<String> keys = filterLabels.keySet();
        keys.forEach(key -> {
            ClickableLabel label = filterLabels.get(key);
            label.setVisible(false);
            label.addLayoutClickListener(e -> removeFilter(key));
        });
    }

    private void removeFilter(String labelKey) {
        switch (labelKey) {
            case "Patronymic":
                authorFilter.setPatronymic("");
                break;
            case "Username":
                authorFilter.setUsername("");
                break;
        }
        refreshContentViewGrid();
    }

    private void setInvisibleFilter(ClickableLabel label) {
        label.setVisible(false);
        label.setCaption("");
    }
}
