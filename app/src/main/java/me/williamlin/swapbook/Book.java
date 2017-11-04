package me.williamlin.swapbook;

/**
 * Created by william on 11/4/17.
 */

public class Book {
    String isbn, title, publisher;
    int edition;
    String[] authors;

    public Book(){}

    public Book(String isbn, String title, int edition, String[] authors, String publisher){
        this.isbn = isbn;
        this.title = title;
        this.edition = edition;
        this.authors = authors;
        this.publisher = publisher;
    }

    public String getIsbn(){ return isbn; }
    public String gettitle(){ return title; }
    public int getEdition(){ return edition; }
    public String[] getAuthors(){ return authors; }
}
