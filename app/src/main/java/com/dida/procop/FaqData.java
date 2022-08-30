package com.dida.procop;

public class FaqData {

    String title;
    String description;
    Boolean expanded;

    public FaqData(String _title, String _description) {
        this.title = _title;
        this.description = _description;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean _expanded) {
        this.expanded = _expanded;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String _title) {
        this.title = _title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

}
