package me.williamlin.swapbook;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class findExchangeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;

    CollectionReference bookRef;
    CollectionReference userRef;

    List<String> everyUID = new ArrayList<>();
    List<String> allYourNeeds = new ArrayList<>();

    Map<String, String> matches = new HashMap<>();

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
                setContentView(R.layout.activity_find_exchange);
            }
        });
    }

    public void findMyNeeds(){
        final int count = 0;
        for(final String uid : everyUID){
            userRef.document(uid).collection("haveBooks").get().addOnSuccessListener(this, new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot documentSnapshots) {
                    for(DocumentSnapshot ds : documentSnapshots.getDocuments()){
                        Log.d("HAHAHAHAHAHAHAH", ds.get("ISBN").toString());
                        if(allYourNeeds.contains(ds.get("ISBN"))){
                            matches.put(uid, ds.get("ISBN").toString());
                        }
                    }

                    Log.d("MAMAMAMAMAM", "Size of Match: " + matches.size());
                }
            });
        }

    }

    public void everyUIDAdd(String uid){ everyUID.add(uid); }
    public void allYourNeedsAdd(String need){ allYourNeeds.add(need); }
}
