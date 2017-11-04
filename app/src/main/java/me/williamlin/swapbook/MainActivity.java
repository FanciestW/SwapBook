package me.williamlin.swapbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db.getInstance();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });

        if(currentUser != null){
            Toast.makeText(MainActivity.this, ("Logged in as " + currentUser.getEmail().toString()), Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_main);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
    }

    public void goToLogin(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void addWant(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a Book You Want");

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_wanthave_form, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String isbn = ((EditText)dialogView.findViewById(R.id.add_wanthave_isbn)).getText().toString();
                String title = ((EditText)dialogView.findViewById(R.id.add_wanthave_title)).getText().toString();

                mAuth = FirebaseAuth.getInstance();
                currentUser = mAuth.getCurrentUser();
                db = FirebaseFirestore.getInstance();

                Map<String, Object> newWant = new HashMap<>();
                newWant.put("ISBN", isbn);
                newWant.put("title", title);

                db.collection("users").document(currentUser.getUid()).collection("wantedBooks").add(newWant);

                Log.d("New Want Details", isbn + ", " + title);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void addNeed(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a Book You Have");

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_wanthave_form, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String isbn = ((EditText)dialogView.findViewById(R.id.add_wanthave_isbn)).getText().toString();
                String title = ((EditText)dialogView.findViewById(R.id.add_wanthave_title)).getText().toString();

                mAuth = FirebaseAuth.getInstance();
                currentUser = mAuth.getCurrentUser();
                db = FirebaseFirestore.getInstance();

                Map<String, Object> newNeed = new HashMap<>();
                newNeed.put("ISBN", isbn);
                newNeed.put("title", title);

                db.collection("users").document(currentUser.getUid()).collection("neededBooks").add(newNeed);

                Log.d("New Want Details", isbn + ", " + title);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void goToExchange(View view){
        Intent intent = new Intent(this, findExchangeActivity.class);
        startActivity(intent);
    }
}
