package me.williamlin.swapbook;

/**
 * Created by william on 11/4/17.
 */

public class Book {
    String isbn, title;
    int edition;
    String[] authors;

    public Book(){}

    public Book(String isbn, String title, int edition, String[] authors){
        this.isbn = isbn;
        this.title = title;
        this.edition = edition;
        this.authors = authors;
    }

    public String getIsbn(){ return isbn; }
    public String gettitle(){ return title; }
    public int getEdition(){ return edition; }
    public String[] getAuthors(){ return authors; }
}
