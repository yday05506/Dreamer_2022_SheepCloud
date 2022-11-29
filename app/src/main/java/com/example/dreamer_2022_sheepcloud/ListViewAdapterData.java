package com.example.dreamer_2022_sheepcloud;

public class ListViewAdapterData {
    private int id;
    private String title;
    private String content;
    private String category;

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    ListViewAdapterData() {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

}
