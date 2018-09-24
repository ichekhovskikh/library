package com.haulmont.testtask.ui.panel;

import com.vaadin.ui.HorizontalLayout;

public interface FilterPanel<T> {
    T getFilter();

    HorizontalLayout getFilterContainer();

    void setFilters(T filter);

    void refreshContentViewGrid();
}
