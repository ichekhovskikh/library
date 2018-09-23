package com.haulmont.testtask.ui.author;

import com.haulmont.testtask.controller.LibraryController;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.model.LibraryModel;
import com.haulmont.testtask.ui.ClickableLabel;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

public class SearchAuthorSubWindow extends AbstractAuthorSubWindow {
    private Author authorFilter;
    private Map<String, ClickableLabel> filterLabels;
    private AuthorView parentView;
    private HorizontalLayout filterContainer;

    public SearchAuthorSubWindow(AuthorView parentView, String caption, LibraryModel model, LibraryController controller) {
        super(caption, model, controller);
        authorFilter = new Author();
        this.parentView = parentView;
        initFilterContainer();
    }

    public Author getAuthorFilter() {
        return this.authorFilter;
    }

    public HorizontalLayout getFilterContainer() {
        return filterContainer;
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
        ok.addClickListener(e -> setFilterLabels());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponents(form, ok);
        mainLayout.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        setContent(mainLayout);
    }

    @Override
    protected void fillInformationAboutAuthor(Author author) {
        author.setPatronymic(patronymic.getValue());
        author.setUsername(username.getValue());
    }

    private void setFilterLabels() {
        filterLabels.values().forEach(this::setInvisibleFilter);
        setFilterWhereNotEmptyFields();
        refreshParentViewGrid();
        close();
    }

    private void setFilterWhereNotEmptyFields() {
        String patronymicFilter = patronymic.getValue();
        if (patronymicFilter != null && !patronymicFilter.equals("")) {
            addLabelOnParentView(filterLabels.get("Patronymic"), "Отчество", patronymicFilter);
        }
        String usernameFilter = username.getValue();
        if (usernameFilter != null && !usernameFilter.equals("")) {
            addLabelOnParentView(filterLabels.get("Username"), "Фамилия", usernameFilter);
        }
    }

    private void refreshParentViewGrid() {
        fillInformationAboutAuthor(authorFilter);
        authorFilter.setName(parentView.getSearchText());
        List<Author> foundAuthors = model.find(authorFilter);
        parentView.refreshGrid(foundAuthors);
    }

    private void initFilterContainer() {
        initFilterLabels();
        filterContainer = new HorizontalLayout();
        filterContainer.setSpacing(true);
        filterContainer.addComponents(filterLabels.get("Username"), filterLabels.get("Patronymic"));
    }

    private void initFilterLabels() {
        filterLabels = newHashMap();
        filterLabels.put("Username", new ClickableLabel());
        filterLabels.put("Patronymic", new ClickableLabel());

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
            case "Username":
                username.setValue("");
                break;
            case "Patronymic":
                patronymic.setValue("");
                break;
        }
    }

    private void addLabelOnParentView(ClickableLabel label, String labelName, String text) {
        label.setCaption(labelName + ": " + text + " X");
        label.setVisible(true);
    }
}
