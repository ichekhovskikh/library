package com.haulmont.testtask.ui;

import com.haulmont.testtask.ui.author.AuthorView;
import com.haulmont.testtask.ui.book.BookView;
import com.haulmont.testtask.ui.genre.GenreView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class NavigatorUI extends UI {
    private Navigator navigator;

    public final static String GENRE_VIEW = "genres";
    public final static String AUTHOR_VIEW = "authors";
    public final static String BOOK_VIEW = "books";

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Библиотека");

        navigator = new Navigator(this, this);
        navigator.addView(GENRE_VIEW, new GenreView());
        navigator.addView(AUTHOR_VIEW, new AuthorView());
        navigator.addView(BOOK_VIEW, new BookView());
        navigator.navigateTo(BOOK_VIEW);
    }
}
