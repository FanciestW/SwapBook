package me.williamlin.swapbook;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.ArrayTable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class findExchangeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;

    CollectionReference bookRef;
    CollectionReference userRef;

    List<String> everyUID = new ArrayList<>();
    List<String> allYourNeeds = new ArrayList<>();

    Map<String, String> matches = new HashMap<>();

    User tempUser;
    Book tempBook;

    private ExchangeListAdapter arrayAdapter;

    @Override
    protected void onStart(){
        super.onStart();
        try{
            this.wait(1300);
        } catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        db = FirebaseFirestore.getInstance();
        bookRef = db.collection("books");
        userRef = db.collection("users");

        setContentView(R.layout.activity_find_exchange);

        arrayAdapter = new ExchangeListAdapter(this, new ArrayList<ExchangeEntry>());
        ListView listView = (ListView)findViewById(R.id.book_exchange_list);
        listView.setAdapter(arrayAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("Position", i + " " + l);
//                List clickedList  = arrayAdapter.getItem(i);
//                Bundle mBundle = new Bundle();
//                mBundle.putParcelable("ListParcelable", clickedList);
//                Log.d("List Clicked: ", clickedList.listTitle + " " + clickedList.getListDescription());
//                Intent intent = new Intent(getBaseContext(), listDetails.class);
//                intent.putExtra("Position", i);
//                intent.putExtras(mBundle);
//                startActivity(intent);
//            }
//        });

        bookRef.get().addOnSuccessListener(this, new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for(DocumentSnapshot d : documentSnapshots.getDocuments()){
                    Log.d("Test", d.toString());
                }
            }
        });


        userRef.document(currentUser.getUid()).collection("wantedBooks").get()
                .addOnSuccessListener(this, new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        for(DocumentSnapshot ds : documentSnapshots.getDocuments()){
                            if(ds.exists()){
                                Log.d("Your Need", "One of your needs: " + ds.get("ISBN").toString());
                                allYourNeedsAdd(ds.get("ISBN").toString());
                            }
                        }
                    }
                });

        userRef.get().addOnSuccessListener(this, new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for(DocumentSnapshot d : documentSnapshots.getDocuments()){
                    if(d.exists()){
                        Log.d("Tester", d.getId());
                        if(!d.getId().equals(currentUser.getUid())) everyUIDAdd(d.getId());
                    }
                }
                findMyNeeds();
            }
        });
    }

    public void findMyNeeds(){
        for(final String uid : everyUID){
            userRef.document(uid).collection("haveBooks").get().addOnSuccessListener(this, new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot documentSnapshots) {
                    for(DocumentSnapshot ds : documentSnapshots.getDocuments()) {
                        Log.d("HAHAHAHAHAHAHAH", ds.get("ISBN").toString());
                        if (allYourNeeds.contains(ds.get("ISBN"))) {
                            matches.put(uid, ds.get("ISBN").toString());
                            arrayAdapter.add(new ExchangeEntry(uid, ds.get("ISBN").toString()));
                            //addMatchEntry(uid, ds.get("ISBN").toString());
                            //getUserAndBook(uid, ds.get("ISBN").toString());
                            //listBook(ds.get("ISBN").toString());
                        }
                    }
                    Log.d("MAMAMAMAMAM", "Size of Match: " + matches.size());
                }
            });
        }

    }

    public void getUserAndBook(String uid, String ISBN){

        userRef.document(uid).get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                tempUser = documentSnapshot.toObject(User.class);
            }
        });

        bookRef.document(ISBN).get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    tempBook = documentSnapshot.toObject(Book.class);
                    if(tempUser != null && tempBook != null) addMatchEntry();
                }

            }
        });
    }


    public void addMatchEntry(){
        ExchangeEntry entry = new ExchangeEntry(tempUser, tempBook);
        Log.d("Added entry", entry.bookTitle);
        arrayAdapter.add(entry);
    }

    public void addMatchEntry(final String uid, final String isbn){

        final ExchangeEntry newEntry = new ExchangeEntry(uid, isbn);
        if(newEntry.userReady) arrayAdapter.add(newEntry);


        Log.d("Done", "Added Match Entry");
    }

    public void listBook(String isbn){
        bookRef.document(isbn).get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Book book = documentSnapshot.toObject(Book.class);
                    ExchangeEntry entry = new ExchangeEntry(book);
                    arrayAdapter.add(entry);
                }

            }
        });
    }

    public void everyUIDAdd(String uid){ everyUID.add(uid); }
    public void allYourNeedsAdd(String need){ allYourNeeds.add(need); }
}
