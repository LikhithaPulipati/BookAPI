package com.example.likhi.booksearchapi;

public class BookModel {

    String title,description,bookimage;

    public BookModel(String title, String description, String bookimage) {
        this.title = title;
        this.description = description;
        this.bookimage = bookimage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookimage() {
        return bookimage;
    }

    public void setBookimage(String bookimage) {
        this.bookimage = bookimage;
    }
}
