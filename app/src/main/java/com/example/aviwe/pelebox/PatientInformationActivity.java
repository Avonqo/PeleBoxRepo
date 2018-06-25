package com.example.aviwe.pelebox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import com.example.aviwe.pelebox.pojos.MediPackClient;

public class PatientInformationActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);

        Intent intent = getIntent();

        MediPackClient med = (MediPackClient) intent.getSerializableExtra("object_of_medi");

        EditText name = findViewById(R.id.name);
        EditText surname = findViewById(R.id.surnname);
        EditText idnumber = findViewById(R.id.idnumber);
        EditText cellnumber = findViewById(R.id.cellnumber);
        EditText captureddate = findViewById(R.id.captureddate);

        EditText NHI = findViewById(R.id.nhi);
        EditText parcelDue1 = findViewById(R.id.edParcelDueDte1);
        EditText parcelDue2 = findViewById(R.id.edParcelDueDte2);

        if (med == null)
        {
            name.setText("");
            surname.setText("");
            idnumber.setText("");
            cellnumber.setText("");
            captureddate.setText("");
            NHI.setText("");
            parcelDue2.setText("");
            parcelDue1.setText("");
        }
        else
        {
            name.setText(med.getPatientFisrtName());
            surname.setText(med.getPatientLastName());
            idnumber.setText(med.getPatientRSA());
            cellnumber.setText(med.getPatientCellphone());
            captureddate.setText(med.getScannedInDateTime());
            NHI.setText(med.getMediPackBarcode());
            parcelDue2.setText(med.getScannedOutDateTime());
            parcelDue1.setText(med.getMediPackDueDateTime());
        }
    }

}
