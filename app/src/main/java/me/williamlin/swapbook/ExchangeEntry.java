package me.williamlin.swapbook;

/**
 * Created by william on 11/5/17.
 */

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ExchangeEntry {
    public String bookTitle, isbn, seller, description, contact;
    public List<String> authors;
    public boolean userReady, bookReady = false;

    User tempUser;
    Book tempBook;

    public ExchangeEntry(Book book){
        this.bookTitle = book.title;
        this.isbn = book.isbn;
        this.authors = book.authors;
        this.description = book.description;
    }

    public ExchangeEntry(String uid, String isbn){
        this.bookTitle = "Title";
        this.isbn = isbn;
        this.seller = uid;
        this.description = "Some thing";
        this.contact = uid;

    }

    public ExchangeEntry(User user, Book book){
        this.bookTitle = book.title;
        this.isbn = book.isbn;
        this.seller = user.getFirstName() + " " + user.getLastName();
        this.description = book.description;
        this.contact = user.getEmail();

    }

    private void fillFromTempBook(){
        this.bookTitle = tempBook.title;
        this.isbn = tempBook.isbn;
        this.description = tempBook.description;
        this.authors = tempBook.authors;
        this.bookReady = true;
    }

    private void fillFromTempUser(){
        this.seller = tempUser.getFirstName() + " " + tempUser.getLastName();
        this.contact = tempUser.getEmail();
        this.userReady = true;
    }
}
