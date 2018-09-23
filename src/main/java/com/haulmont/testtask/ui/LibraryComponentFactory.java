package com.haulmont.testtask.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

public class LibraryComponentFactory {

    public static Component createLibraryHeader(Component component) {
        MenuBar menu = new MenuBar();
        menu.setWidth(component.getWidth(), component.getWidthUnits());

        MenuBar.Command itemCommand = (MenuBar.Command) selectedItem -> {
            String itemName = selectedItem.getText();
            component.getUI().getNavigator().navigateTo(viewName(itemName));
        };

        menu.addItem("Книги", null, itemCommand);
        menu.addItem("Авторы", null, itemCommand);
        menu.addItem("Жанры", null, itemCommand);
        return menu;
    }

    public static Component createLibraryTittle(String text) {
        HorizontalLayout layoutForTittle = new HorizontalLayout();
        Label tittle = new Label(text);
        tittle.addStyleName("h2");
        layoutForTittle.addComponent(tittle);
        return layoutForTittle;
    }

    public static Button createButtonEdit() {
        Button button = new Button("Редактировать", FontAwesome.PENCIL);
        button.setVisible(false);
        return button;
    }

    public static Button createButtonAdd() {
        return new Button("Добавить", FontAwesome.PLUS);
    }

    public static Button createButtonRemove() {
        Button button = new Button("Удалить", FontAwesome.TRASH);
        button.setVisible(false);
        return button;
    }

    private static String viewName(String itemName) {
        switch (itemName) {
            case "Книги":
                return NavigatorUI.BOOK_VIEW;
            case "Авторы":
                return NavigatorUI.AUTHOR_VIEW;
            case "Жанры":
                return NavigatorUI.GENRE_VIEW;
            default:
                throw new IllegalArgumentException();
        }
    }
}
