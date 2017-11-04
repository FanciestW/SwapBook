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
        builder.setTitle("Add New List");

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_want_form, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = ((EditText)dialogView.findViewById(R.id.add_list_title)).getText().toString();
                String desc = ((EditText)dialogView.findViewById(R.id.add_list_desc)).getText().toString();

                Log.d("New List Details", title + ", " + desc);

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
}
