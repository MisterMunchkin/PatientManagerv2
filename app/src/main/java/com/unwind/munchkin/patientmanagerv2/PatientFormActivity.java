package com.unwind.munchkin.patientmanagerv2;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.unwind.munchkin.patientmanagerv2.R.id.newpatient_Button;

public class PatientFormActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    private EditText patientfirstname;
    private EditText patientlastname;
    private EditText patientIDText;
  //  private int year, month, day;
    private Button submit;
 //   Calendar myCalendar;

    private String UID;


//    static final int DIALOG_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);

        mAuth = FirebaseAuth.getInstance();
        UID = mAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance().getReference().child("Patients").child(UID);
        patientIDText = (EditText)findViewById(R.id.patientID_Input);
        patientfirstname = (EditText)findViewById(R.id.patientfirstname_Input);
        patientlastname = (EditText)findViewById(R.id.patientlastname_Input);
        submit = (Button)findViewById(R.id.newpatient_Button);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertToFirebase();
            }
        });
    }
    private void insertToFirebase(){
        Patient patient = new Patient();
        String firstname, lastname;
        String patientID;
        DatabaseReference patientRef;

        patientID = patientIDText.getText().toString();
        firstname = patientfirstname.getText().toString();
        lastname = patientlastname.getText().toString();

        patient.setFirstname(firstname);
        patient.setLastname(lastname);
        patient.setPatientID(patientID);

        patientRef = database.push();

        patientRef.setValue(patient, new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(PatientFormActivity.this, "insert successful!", Toast.LENGTH_LONG).show();
            }
        });
        patientIDText.setText("");
        patientfirstname.setText("");
        patientlastname.setText("");
    }

}
