package com.example.aviwe.pelebox.Scanin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aviwe.pelebox.DataBaseHelpe;
import com.example.aviwe.pelebox.MainActivity;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.example.aviwe.pelebox.R;
import butterknife.ButterKnife;

public class ScannedInAdapter extends RecyclerView.Adapter<ScannedInAdapter.ViewHolder>
{
    private ArrayList<MediPackClient> mArrayList;
    private ArrayList<MediPackClient> mFilteredList;
    private Context mContext;
    private DataBaseHelpe helper;
    private String edName,edSurname,edID,edCellNumber,edDueDate;
    MediPackClient mediPackClient;
    AlertDialog alertDialog;
    EditText Dialogname,Dialogsurname,DialogIdno,DialogCellNumber,DialogDueDate;
    DatePickerDialog.OnDateSetListener dateListener;
    String selectedDate,barcode,formattedDate;
    int databaseStatus,status,dirtyFlag;
    Button saveData,cancelData,selectDate;

    public ScannedInAdapter(ArrayList<MediPackClient> arrayList,Context mContext) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_med, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position)
    {
        //Getting all the user data from the database
        String s= mFilteredList.get(position).getMediPackDueDateTime();
        holder.surname.setText("Patient Surname          : "  + mFilteredList.get(position).getPatientLastName());
        holder.name.setText("Patient Name               : "  + mFilteredList.get(position).getPatientFisrtName() );
        holder.id.setText("Patient ID Number       : " + mFilteredList.get(position).getPatientRSA());
        holder.cellphone.setText("Patient Cellphone        : " + mFilteredList.get(position).getPatientCellphone());
        holder.nhiNumber.setText("Patient NHI Number    : " + mFilteredList.get(position).getMediPackBarcode());
        holder.duedate.setText("Parcel Due Date          :  "  + s);

        //Getting the position of the item on the recycler view
         mediPackClient = mFilteredList.get(position);

         //Accept scanin parcel functionality
         holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (validateTime() == true)
                {
                    new AlertDialog.Builder(mContext)
                            .setMessage("Are you sure you want to accept the parcel?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    //Getting the barcode at the specific position
                                    barcode = mFilteredList.get(position).getMediPackBarcode();

                                    //Getting the medi pack id at the specific position
                                    databaseStatus = mFilteredList.get(position).getMediPackStatusId();

                                    //Updating the medi pack id and aslo dirty flag after scan in
                                    status = 2;
                                    dirtyFlag = 2;

                                    if (databaseStatus == status)
                                    {
                                        Toast.makeText(mContext, "Medi Pack Parcel has already been sccanned in", Toast.LENGTH_LONG).show();

                                        //Removing the item from the list
                                        mFilteredList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, mFilteredList.size());
                                    }
                                    else
                                    {
                                        helper = new DataBaseHelpe(mContext);
                                        helper.UpdateParcelStatus(barcode, status, dirtyFlag);
                                        Toast.makeText(mContext, "Successfully sccanned in ", Toast.LENGTH_LONG).show();

                                        //Display and update the current date the medi parecel was scanned in
                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                        formattedDate = df.format(c.getTime());

                                        //Calling the update method from the database to update the date
                                        helper.UpdateParcelDateScannedIn(barcode, formattedDate);

                                        //Removing the item from the list after scan in successfully
                                        mFilteredList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, mFilteredList.size());
                                    }
                                }
                            }).create().show();
                }
                else
                {
                    Toast.makeText(mContext, "Your session has expired", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                }
            }

        });

        //Edit medi pack parcel functionality
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view)
            {
                if (validateTime())
                {
                    View layoutview = LayoutInflater.from(mContext).inflate(R.layout.editdialog_layout,null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setView(layoutview);
                    builder.setTitle("Please enter medi pack client details");

                      Dialogname = layoutview.findViewById(R.id.editName);
                      Dialogsurname = layoutview.findViewById(R.id.editSurname);
                      DialogIdno = layoutview.findViewById(R.id.editId);
                      DialogCellNumber= layoutview.findViewById(R.id.editCellphone);
                      DialogDueDate = layoutview.findViewById(R.id.editDueDate);

                      //Setting the edit text field to allow single line input
                      Dialogname.setSingleLine(true);
                      DialogCellNumber.setSingleLine(true);
                      DialogDueDate.setSingleLine(true);
                      Dialogsurname.setSingleLine(true);
                      DialogIdno.setSingleLine(true);


                      //Setting the edited data to the fields
                     Dialogname.setText(mFilteredList.get(position).getPatientFisrtName());
                     Dialogsurname.setText(mFilteredList.get(position).getPatientLastName());
                     DialogIdno.setText(mFilteredList.get(position).getPatientRSA());
                     DialogCellNumber.setText(mFilteredList.get(position).getPatientCellphone());
                     DialogDueDate.setText(mFilteredList.get(position).getMediPackDueDateTime());


                     //Buttons in the dialog box when editing the parcel information
                     saveData = layoutview.findViewById(R.id.btnSave);
                     cancelData = layoutview.findViewById(R.id.btnCancel);
                     selectDate = layoutview.findViewById(R.id.btnselectedate);

                        saveData.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                edName = Dialogname.getText().toString().trim();
                                edSurname = Dialogsurname.getText().toString().trim();
                                edID = DialogIdno.getText().toString().trim();
                                edDueDate = DialogDueDate.getText().toString().trim();
                                edCellNumber = DialogCellNumber.getText().toString().trim();

                               if(isDataValid() == true)
                               {
                                   holder.surname.setText("Patient Surname         : "  + edSurname);
                                   holder.name.setText("Patient Name              : " + edName);
                                   holder.id.setText("Patient ID Number      : " + edID);
                                   holder.cellphone.setText("Patient Cellphone        : " + edCellNumber);
                                   holder.duedate.setText("Parcel Due Date          :  " + edDueDate);

                                   //Declaring the field
                                   String barcode = mFilteredList.get(position).getMediPackBarcode();
                                   int status = 1;

                                   helper = new DataBaseHelpe(mContext);
                                   MediPackClient med = new MediPackClient(edName, edSurname, edID, barcode, edCellNumber, edDueDate, status);

                                   ArrayList<MediPackClient> mediPack = helper.getAllMediPack();

                                   Boolean isFound = false;
                                   for (MediPackClient medi : mediPack) {
                                       if (medi.getMediPackBarcode().equals(barcode))
                                       {
                                           isFound = true;
                                       }
                                   }
                                   if (isFound) {
                                       helper.UpdateParcelfromScan(barcode, edName, edCellNumber, edSurname, edID, edDueDate);

                                   } else{
                                       helper.addDataFromCloud(med);
                                   }

                                   Toast.makeText(mContext, "Parcel successfully updated ", Toast.LENGTH_LONG).show();
                                   alertDialog.dismiss();
                           }
                        }

                    });


                    selectDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            //Trying something
                            dateListener = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int date)
                                {
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    month = month+1;

                                    selectedDate = year + "-"+ month + "-" + date;
                                    Date dater = null;
                                    try {
                                        dater = df.parse(selectedDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    DialogDueDate.setText(df.format(dater));
                                }
                            };

                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int date = calendar.get(Calendar.DAY_OF_MONTH);
                            int month = calendar.get(Calendar.MONTH);

                            DatePickerDialog dialog = new DatePickerDialog(mContext, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener, year, month, date);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                            dialog.show();
                        }
                    });

                    cancelData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog =builder.create();
                alertDialog.show();
                }
                else
                {
                    Toast.makeText(mContext, "Your session has expired", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

        //Cancel parcel functionality
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mFilteredList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mFilteredList.size());
            }
        });
    }

    public Boolean  validateTime()
    {
        boolean valid = false;

        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String d1 = df.format(c.getTime());
        Date currentDate = null;

        //change a string to a datetime format
        String timeoutDb = MainActivity.newTimeout;
        try {
            if (timeoutDb != null)
            {
                if (MainActivity.ckRemember.isChecked())
                {
                    Date d = df.parse(timeoutDb);
                    currentDate = df.parse(d1);
                    c.setTime(d);
                    c.add(Calendar.MINUTE, 60);

                    String newTime = df.format(c.getTime());
                    d=df.parse(newTime);

                    if (d.getTime() > currentDate.getTime())
                    {
                        valid=true;
                    }
                }
                else
                {
                    Date d= df.parse(timeoutDb);
                    currentDate = df.parse(d1);

                    if (d.getTime() > currentDate.getTime()) {

                        valid=true;
                    }
                }
            }
            else
            {
                valid = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return valid;
    }

    public boolean isDataValid()
    {
        boolean isvalid = true;

        if (edName.isEmpty()) {
            Dialogname.setError("Please enter patient name");
            isvalid = false;
        }

        //Checking the name if it doesnt contain any digit
        if (edName.length() > 0) {
            for (int i = 0; i < edName.length(); i++) {
                if (Character.isDigit(edName.charAt(i))) {
                    Dialogname.setError("Patient name must not contain digits");
                    isvalid = false;
                }
            }
        }

        //Surname validatation
        if (edSurname.isEmpty()) {
            Dialogsurname.setError("Please enter patient surame");
            isvalid = false;;
        }
        else if (edSurname.length() > 0)
        {
            for (int i = 0; i < edSurname.length(); i++) {
                if (Character.isWhitespace(edSurname.charAt(i))) {
                    Dialogsurname.setError("Patient surname must not contain spaces");
                    isvalid = false;
                }
            }
        }

        //Checking if the surname entered does not contain digits
        if (edSurname.length() > 0) {
            for (int i = 0; i < edSurname.length(); i++) {
                if (Character.isDigit(edSurname.charAt(i))) {
                    Dialogsurname.setError("Patient surname must not contain digits");
                    isvalid = false;
                }
            }
        }

        if (edID.isEmpty()) {
            DialogIdno.setError("Please enter patient ID Number");
            isvalid = false;
        }

        if (edID.length() != 13) {
            DialogIdno.setError("Please enter a valid patient ID Number");
            isvalid = false;
        }
        if (edID.length() > 0) {
            for (int i = 0; i < edID.length(); i++) {
                if (Character.isLetter(edID.charAt(i))) {
                    DialogIdno.setError("Patient ID Number must not contain letters");
                    isvalid = false;
                }
            }
        }

        if (TextUtils.isEmpty(edDueDate))
        {
            DialogDueDate.setError("Field must not be empty");
            isvalid = false;
        }

        if(!TextUtils.isEmpty(edCellNumber))
        {
            if (isPhoneValid(edCellNumber) == false || edCellNumber.length() < 10 || edCellNumber.length() > 10) {

                DialogCellNumber.setError("Please enter valid phone numbers");
                isvalid = false;
            }
        }

        return isvalid;
    }

    //Method for validation the contact details
    public boolean isPhoneValid(String phone)
    {
        boolean isValid = false;

        String[] validNum = {"076","071","082","083","073","072","081","079","061","062","060","063","074","078","084","080","086","088","085","077","075","087","089"};

        String num = phone.substring(0, 3);

        for(int x = 0;x<validNum.length;x++)
        {
            if(num.equalsIgnoreCase(validNum[x]))
            {
                isValid = true;
            }
        }
        return isValid;
    }

    @Override
    public int getItemCount()
    {
        return mFilteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name = itemView.findViewById(R.id.txtname);
        TextView surname = itemView.findViewById(R.id.txtsurname);
        TextView id = itemView.findViewById(R.id.txtID);
        TextView duedate = itemView.findViewById(R.id.txtDueDate);
        TextView accept = itemView.findViewById(R.id.txtAccept);
        TextView decline = itemView.findViewById(R.id.txtDecline);
        TextView edit = itemView.findViewById(R.id.txtEdit);
        TextView cellphone = itemView.findViewById(R.id.cellphone);
        TextView nhiNumber = itemView.findViewById(R.id.txtNhi);

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

