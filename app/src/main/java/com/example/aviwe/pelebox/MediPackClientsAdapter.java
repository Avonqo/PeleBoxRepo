package com.example.aviwe.pelebox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Button;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import butterknife.ButterKnife;

/**
 * Created by sarak on 1/29/2018.
 */

public class MediPackClientsAdapter extends RecyclerView.Adapter<MediPackClientsAdapter.ViewHolder> implements Filterable
{
    private ArrayList<MediPackClient> mArrayList;
    private ArrayList<MediPackClient> mFilteredList;
    private MediPackClientsAdapterListener listener;
    private Context context;

    public MediPackClientsAdapter(ArrayList<MediPackClient> arrayList) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
    }

    public MediPackClientsAdapter(Context context,ArrayList<MediPackClient> arrayList,MediPackClientsAdapterListener listener) {
        this.context = context;
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
        this.listener = listener;
    }

    @Override
    public MediPackClientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MediPackClientsAdapter.ViewHolder holder, int position)
    {
            //Getting Current time
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String d1 = df.format(c.getTime());
            Date d = new Date();
            Date currentDate = null;

            String mediPackDueDateTime = mFilteredList.get(position).getMediPackDueDateTime();
            String newDate = mediPackDueDateTime.substring(0,10);

            try
            {
                currentDate = df.parse(d1);
                d = df.parse(newDate);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

            //Getting data
            String dueDateTime = mFilteredList.get(position).getMediPackDueDateTime();
            String name =mFilteredList.get(position).getPatientFisrtName();
            String surname =mFilteredList.get(position).getPatientLastName();
            String id =mFilteredList.get(position).getPatientRSA();
            String cellphone =mFilteredList.get(position).getPatientCellphone();
            String nhi =mFilteredList.get(position).getMediPackBarcode();
            String fname =surname.substring(0,1);

            if (d.getTime() < currentDate.getTime())
            {
                holder.surname.setText("Patient Surname     : " + surname);
                holder.name.setText("Patient Name          : "  + name);
                holder.expiredLabel.setText("Over Due");
                holder.id.setText("Patient ID Number  : " + id);
                holder.cellphone.setText("Patient Cellphone      : " + cellphone);
                holder.nhiNumber.setText("Patient NHI Number  : " + nhi);
                holder.duedate.setText("Parcel Due Date         : " + dueDateTime);
                holder.firstLetter.setText(fname);
            }
            else if((d.getTime() >= currentDate.getTime()))
            {
                holder.surname.setText("Patient Surname     : " + surname);
                holder.name.setText("Patient Name          : "  + name);
                holder.expiredLabel.setText("");
                holder.id.setText("Patient ID Number  : " + id);
                holder.cellphone.setText("Patient Cellphone      : " + cellphone);
                holder.nhiNumber.setText("Patient NHI Number  : " + nhi);
                holder.duedate.setText("Parcel Due Date         : " + dueDateTime);
                holder.firstLetter.setText(fname);
            }
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

                if (charString.isEmpty())
                {
                   mFilteredList= mArrayList;
                } else {
                    ArrayList<MediPackClient> filteredList = new ArrayList<>();

                    for (MediPackClient med : mArrayList) {

                        if (med.getPatientFisrtName().toLowerCase().contains(charString) || med.getPatientLastName().toLowerCase().contains(charString) || med.getPatientRSA().toLowerCase().contains(charString) || med.getMediPackBarcode().toUpperCase().contains(charSequence) || med.getPatientCellphone().toUpperCase().contains(charSequence))
                        {
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

