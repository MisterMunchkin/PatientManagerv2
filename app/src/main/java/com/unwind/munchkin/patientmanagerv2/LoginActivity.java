package com.unwind.munchkin.patientmanagerv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import org.w3c.dom.Text;

import static com.google.firebase.auth.FirebaseAuth.*;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private Button LoginButton;
    private TextView registerLink;

    private FirebaseAuth mAuth;
    private AuthStateListener mAuthListener;

    private ProgressDialog progress;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progress = new ProgressDialog(this);
        emailInput = (EditText)findViewById(R.id.email_input);
        passwordInput = (EditText)findViewById(R.id.pass_input);

        LoginButton = (Button)findViewById(R.id.LoginButton);

        LoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startSignIn();
            }
        });
        registerLink = (TextView)findViewById(R.id.signUpRedirect);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        mAuth = getInstance();

        mAuthListener = new AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    //user is signed in
                    startActivity(new Intent(LoginActivity.this, HomePagev2.class));
                   // Log.d(TAG, "onAuthStateChanged: signed_in:" + user.getUid());
                }else{
                    //user is signed out
                    //Intent LoginIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                   // LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(LoginIntent);
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };
    }

    private void startSignIn(){
        final String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
        }else{
            progress.setMessage("Loggin in...");
            progress.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progress.dismiss();

                        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "password incorrect", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(LoginActivity.this, "email incorrect", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }else{
                        progress.dismiss();
                    }
                }
            });
        }

    }

    @Override
    public void onStart(){
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //do number 4,,, sign up new users, use the firebase class to place the methods into the create account java class
}
