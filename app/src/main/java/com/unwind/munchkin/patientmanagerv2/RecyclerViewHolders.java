package com.unwind.munchkin.patientmanagerv2;

import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Munchkin on 8/25/2017.
 */

class RecyclerViewHolders extends RecyclerView.ViewHolder {
    private List<Patient> patientObject;
    public ImageView deleteIcon;
    private FirebaseAuth mAuth;
    private String FirebaseUID;

    public RecyclerViewHolders(View itemView, List<Patient> patient) {
        super(itemView);
        this.patientObject = patientObject;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUID = mAuth.getCurrentUser().getUid();
       // deleteIcon = (ImageView)itemView.findViewById(R.id.patientDelete);

       /* deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Delete icon has been clicked", Toast.LENGTH_LONG).show();
                Patient patient = patientObject.get(getAdapterPosition());
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Patients").child(FirebaseUID);
                Query applesQuery = ref.orderByChild(ref.getKey()).equals(patient);
            }
        });
        */
    }
}
