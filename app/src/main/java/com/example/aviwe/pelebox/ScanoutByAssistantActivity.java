package com.example.aviwe.pelebox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import com.example.aviwe.pelebox.utils.ConstantMethods;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScanoutByAssistantActivity extends AppCompatActivity 
{
    EditText PatientFisrtName,PatientLastName,PatientRSA,PatientCellphone,ScannedInDateTime,MediPackDueDateTime,MediPackBarcode,MediPackStatus;
    private MediPackClient med;
    SimpleDateFormat df ;
    Calendar c;
    Button BtnCollect;

    private DataBaseHelpe myHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanout_by_assistant);

        med = new MediPackClient();
        myHelper = new DataBaseHelpe(this);

        PatientFisrtName = findViewById(R.id.name);
        PatientLastName = findViewById(R.id.surnname);
        PatientRSA = findViewById(R.id.idNumber);
        PatientCellphone = findViewById(R.id.cellphone);
        MediPackDueDateTime = findViewById(R.id.duedate);
        MediPackBarcode = findViewById(R.id.nhi);
        MediPackStatus = findViewById(R.id.status);
        ScannedInDateTime = findViewById(R.id.capaturedDate);
        
         //Getting the current date
         c = Calendar.getInstance();
         df = new SimpleDateFormat("yyyy-MM-dd");

         BtnCollect = findViewById(R.id.collect);
        final Intent intent = getIntent();
        
        //Getting the pojo object medi pack
        med = (MediPackClient) intent.getSerializableExtra("object_of_medi");

        //Setting data 
        PatientFisrtName.setText(med.getPatientFisrtName());
        PatientLastName.setText(med.getPatientLastName());
        PatientRSA.setText(med.getPatientRSA());
        PatientCellphone.setText(med.getPatientCellphone());
        ScannedInDateTime.setText(med.getScannedInDateTime());
        MediPackDueDateTime.setText(med.getMediPackDueDateTime());
        MediPackBarcode.setText(med.getMediPackBarcode());

        getMediPackIdStatus();

        //Collect button functions
        BtnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int NewStatus,dirtyFlag;
                String date,idNo;

                if (ConstantMethods.validateTime() == true)
                {
                    if (med.getMediPackStatusId() == 2)
                    {
                        NewStatus = 4;
                        dirtyFlag = 2;

                        //Current time
                        date = df.format(c.getTime());
                        idNo = med.getPatientRSA();

                        //Calling the helper method to mupdate the medipack id and dirty flag
                        myHelper.UpdateCollectStatus(NewStatus, idNo, date, dirtyFlag);

                        //Clearing the fields after collecting
                        PatientFisrtName.setText(" ");
                        PatientLastName.setText(" ");
                        PatientRSA.setText(" ");
                        PatientCellphone.setText(" ");
                        ScannedInDateTime.setText(" ");
                        MediPackBarcode.setText(" ");
                        MediPackDueDateTime.setText(" ");
                        MediPackStatus.setText("");

                        Toast.makeText(ScanoutByAssistantActivity.this, "Parcel successfully scanned out  ", Toast.LENGTH_LONG).show();
                    }
                    else {

                        //Clearing the fields after collecting
                        PatientFisrtName.setText(" ");
                        PatientLastName.setText(" ");
                        PatientRSA.setText(" ");
                        PatientCellphone.setText(" ");
                        ScannedInDateTime.setText(" ");
                        MediPackBarcode.setText(" ");
                        MediPackDueDateTime.setText(" ");
                        MediPackStatus.setText("");

                        Toast.makeText(ScanoutByAssistantActivity.this, "Parcel can not be scanned out  ", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                    //Timeout alert
                    AlertDialog.Builder builder = new AlertDialog.Builder(ScanoutByAssistantActivity.this);
                    builder.setTitle("Timeout Warning !");
                    builder.setMessage("Your time has expired .Please login again");
                    builder.setIcon(R.drawable.ic_warning_black_24dp);

                    builder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }
            }
        });

    }

    //Assign status based on the medi pack id
    public void getMediPackIdStatus()
    {
        //Getting the medipack id from the database
        int status = med.getMediPackStatusId();

        if (status == 0) {
            MediPackStatus.setText("Uploaded");
        } else if (status == 1) {
            MediPackStatus.setText("Booking  for scanning ");
        } else if (status == 2) {
            MediPackStatus.setText("Scanned In");
        } else if (status == 3) {
            MediPackStatus.setText("Scanned Out Collected ");
            BtnCollect.setVisibility(View.INVISIBLE);
        } else if (status == 4) {
            MediPackStatus.setText("Collected by Patient With Assistance From Admin");
            BtnCollect.setVisibility(View.INVISIBLE);
        }
        else if(status == 5)
        {
            MediPackStatus.setText("Medication Returned Due to Non Collections");
        }
    }

}



