package uk.co.compendiumdev.javafortesters.domain.cannedtext;

public class CannedTextItem {
    private final String textValue;
    private final String label;
    private final String id;

    public CannedTextItem(String textValue) {
        this(textValue, textValue);
    }

    public CannedTextItem(String label, String textValue) {
        this.id = new StringBuilder().append(System.currentTimeMillis()).append(System.nanoTime()).toString();
        this.textValue = textValue;
        this.label = label;
    }

    public String getTextValue() {
        return textValue;
    }

    public String getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }
}
