package com.example.aviwe.pelebox.report;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.aviwe.pelebox.R;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> implements Filterable
{
    private ArrayList<MediPackClient> mArrayList;
    private ArrayList<MediPackClient> mFilteredList;
    private MediPackClientsAdapterListener listener;

    public ReportAdapter(ArrayList<MediPackClient> arrayList) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ReportAdapter.ViewHolder holder, int position)
    {
            String dueDateTime,name,surname,id,cellphone,nhi,fname;

            dueDateTime  = mFilteredList.get(position).getMediPackDueDateTime();
            name  =mFilteredList.get(position).getPatientFisrtName();
            surname  =mFilteredList.get(position).getPatientLastName();
            id  =mFilteredList.get(position).getPatientRSA();
            cellphone  =mFilteredList.get(position).getPatientCellphone();
            nhi  =mFilteredList.get(position).getMediPackBarcode();
            fname  =surname.substring(0,1);

            holder.surname.setText("Patient Surname     : " + surname);
            holder.name.setText("Patient Name          : " + name);
            holder.expiredLabel.setText("Collected");
            holder.expiredLabel.setTextColor(Color.GREEN);
            holder.id.setText("Patient ID Number  : " + id);
            holder.cellphone.setText("Patient Cellphone      : " + cellphone);
            holder.nhiNumber.setText("Patient NHI Number  : " + nhi);
            holder.duedate.setText("Parcel Due Date         : " + dueDateTime);
            holder.firstLetter.setText(fname);
    }

    @Override
    public int getItemCount()
    {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                   mFilteredList= mArrayList;
                }
                else {
                    ArrayList<MediPackClient> filteredList = new ArrayList<>();

                    for (MediPackClient med : mArrayList) {

                        if (med.getPatientFisrtName().toLowerCase().contains(charString) || med.getPatientLastName().toLowerCase().contains(charString) || med.getPatientRSA().toLowerCase().contains(charString) || med.getMediPackBarcode().toUpperCase().contains(charSequence) || med.getPatientCellphone().toUpperCase().contains(charSequence)) {

                            filteredList.add(med);
                        }
                    }
                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<MediPackClient>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView surname = itemView.findViewById(R.id.txtSurname);
        TextView name = itemView.findViewById(R.id.txtNames);
        TextView id = itemView.findViewById(R.id.txtID);
        TextView duedate = itemView.findViewById(R.id.txtDueDate);
        TextView cellphone = itemView.findViewById(R.id.txtCellphone);
        TextView nhiNumber = itemView.findViewById(R.id.txtNhiNumber);
        TextView expiredLabel = itemView.findViewById(R.id.txtexpired);
        Button firstLetter = itemView.findViewById(R.id. btnFirstLetter);


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(mFilteredList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface MediPackClientsAdapterListener {
        void onContactSelected(MediPackClient mediPackClient);
    }

}

