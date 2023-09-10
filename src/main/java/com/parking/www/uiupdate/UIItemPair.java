package com.parking.www.uiupdate;

import com.vaadin.flow.component.UI;

public class UIItemPair {
    private final UI ui;
    private final Object item;

    public UIItemPair(UI ui, Object item) {
        this.ui = ui;
        this.item = item;
    }

    public UI getUi() {
        return ui;
    }

    public Object getItem() {
        return item;
    }
}
