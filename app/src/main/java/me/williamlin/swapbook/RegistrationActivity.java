package me.williamlin.swapbook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_registration);
    }

    public void goToLogin(View view){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void registerUser(View view){
        if(mAuth == null) mAuth = FirebaseAuth.getInstance();
        if(db == null) db = FirebaseFirestore.getInstance();

        final String email = ((EditText)findViewById(R.id.input_email)).getText().toString();
        final String password = ((EditText)findViewById(R.id.input_password)).getText().toString();

        final String fname = ((EditText)findViewById(R.id.input_fname)).getText().toString();
        final String lname = ((EditText)findViewById(R.id.input_lname)).getText().toString();
        final String university = ((EditText)findViewById(R.id.input_university)).getText().toString();

        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("REGISTRATION", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                User newUser = new User(user.getUid(), fname, lname, university, email);
                                db.collection("users").document(user.getUid()).set(newUser);
                                goToMain();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("REGISTRATION", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        } catch(Exception e){
            Toast.makeText(RegistrationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
