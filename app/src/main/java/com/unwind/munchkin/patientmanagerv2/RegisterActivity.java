package com.unwind.munchkin.patientmanagerv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstnameField;
    private EditText lastnameField;
    private EditText emailField;
    private EditText passwordField;

    private Button registerButton;

    private FirebaseAuth mAuth;

    private ProgressDialog progress;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance().getReference().child("Users");

        progress = new ProgressDialog(this);

        firstnameField = (EditText)findViewById(R.id.firstname_input);
        lastnameField = (EditText)findViewById(R.id.lastname_input);
        emailField = (EditText)findViewById(R.id.email_input);
        passwordField = (EditText)findViewById(R.id.password_input);

        registerButton = (Button)findViewById(R.id.Register_Button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
    }

    public void goToLogin(View view){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    private void startRegister() {

        final String firstname;
        final String lastname;
        String email;
        String password;

        firstname = firstnameField.getText().toString().trim();
        lastname = lastnameField.getText().toString().trim();
        email = emailField.getText().toString().trim();
        password = passwordField.getText().toString().trim();

        if(!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) &&
                !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progress.setMessage("Signing up...");
            progress.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String userID = mAuth.getCurrentUser().getUid();

                        DatabaseReference currentUser = database.child(userID);

                        currentUser.child("firstname").setValue(firstname);
                        currentUser.child("lastname").setValue(lastname);
                        currentUser.child("image").setValue("default");

                        progress.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, HomePagev2.class);

                        startActivity(intent);
                    }
                }
            });
        }
    }
}
