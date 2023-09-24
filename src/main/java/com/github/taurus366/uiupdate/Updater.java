package com.github.taurus366.uiupdate;

import com.vaadin.flow.component.UI;

import java.util.*;
import java.util.stream.Stream;

public class Updater {
    private final Map<UI, Map<String, Object>> uiListMap = new HashMap<>();

    public Updater() {
    }

    public void addItemToUi(UI ui, String itemGetByName, Object item) {
       if(this.uiListMap.containsKey(ui)){
           this.uiListMap.get(ui).put(itemGetByName, item);
       } else {
           this.uiListMap.put(ui, Map.of(itemGetByName, item));
       }
    }

    public void removeItemFromUi(UI ui) {
        this.uiListMap.remove(ui);
    }

    public Stream<UIItemPair> getItemByName(String itemGetByName) {
        return uiListMap.entrySet()
                .stream()
                .flatMap(entry -> entry.getValue()
                        .entrySet()
                        .stream()
                        .filter(e -> e.getKey().equals(itemGetByName))
                        .map(e -> new UIItemPair(entry.getKey(), e.getValue())));
    }
    public Map<UI, Map<String, Object>> getUiListMap() {
        return uiListMap;
    }
}

