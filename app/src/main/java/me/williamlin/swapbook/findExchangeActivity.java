package me.williamlin.swapbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
        db.collection("users").document().collection("haveBooks").whereEqualTo("ISBN", )
    }
}
