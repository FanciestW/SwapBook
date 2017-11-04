package me.williamlin.swapbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class findExchangeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        db = FirebaseFirestore.getInstance();

        findMatches(getCurrentFocus());
        setContentView(R.layout.activity_find_exchange);
    }

    public void findMatches(View view){
        CollectionReference haveBooksRef = db.collection("users").document().collection("haveBooks");
        Query q = haveBooksRef.whereEqualTo("ISBN", "123456789");
        q.add

        Log.d("test", "test");
    }
}
