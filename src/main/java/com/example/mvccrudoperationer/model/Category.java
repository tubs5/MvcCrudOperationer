package com.example.mvccrudoperationer.model;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SpellCheckingInspection")
public enum Category {
    //Kod tagen ifrån https://www.baeldung.com/java-enum-values
    travel("Resor till och från jobbet"),
    meetings("Lunchmöten"),
    Overtime("Övertidsarbete"),
    Other("Övrigt");
    public final String label;
    Category(String label){
        this.label = label;
    }
    private static final Map<String, Category> BY_LABEL = new HashMap<>();

    static {
        for (Category e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }
    public static Category valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
