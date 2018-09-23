package com.haulmont.testtask.ui;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ClickableLabel extends VerticalLayout {
    private Label label;

    public ClickableLabel() {
        label = new Label();
        addComponent(label);
    }

    public ClickableLabel(String value) {
        label = new Label (value, ContentMode.HTML);
        addComponent(label);
    }

    @Override
    public String getCaption() {
        return label.getCaption();
    }

    @Override
    public void setCaption(String text) {
        label.setCaption(text);
    }
}
