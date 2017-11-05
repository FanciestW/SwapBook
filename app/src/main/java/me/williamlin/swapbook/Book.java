package me.williamlin.swapbook;

import java.util.List;

/**
 * Created by william on 11/4/17.
 */

public class Book {
    String isbn, title, publisher, description;
    int edition;
    List<String> authors;

    public Book(){}

    public Book(String isbn, String title, int edition, List<String> authors, String publisher, String description){
        this.isbn = isbn;
        this.title = title;
        this.edition = edition;
        this.authors = authors;
        this.publisher = publisher;
        this.description = description;
    }

    public String getIsbn(){ return isbn; }
    public String gettitle(){ return title; }
    public int getEdition(){ return edition; }
    public List<String> getAuthors(){ return authors; }
    public String getDescription(){ return description; }
}
