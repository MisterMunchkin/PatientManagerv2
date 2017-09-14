package com.unwind.munchkin.patientmanagerv2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Munchkin on 8/25/2017.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<Patient> patient;
    protected Context context;

    public RecyclerViewAdapter(Context context, List<Patient> allPatient) {
        this.patient = allPatient;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list,parent, false);
        viewHolder = new RecyclerViewHolders(layoutView, patient);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.patient.size();
    }
}
