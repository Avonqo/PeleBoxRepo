package com.example.aviwe.pelebox.Scanout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.aviwe.pelebox.MainActivity;
import com.example.aviwe.pelebox.R;
import com.example.aviwe.pelebox.DataBaseHelpe;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import com.example.aviwe.pelebox.search_parcel.SearchPatientActivity;
import com.example.aviwe.pelebox.utils.ConstantMethods;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScanOoutActivity extends AppCompatActivity {
    ArrayList<MediPackClient> mediPackList;
    private MediPackClient med;
    private DataBaseHelpe myHelper;
    EditText inputType1, edPin, PatientFisrtName, PatientLastName, PatientRSA, PatientCellphone, ScannedInDateTime, MediPackDueDateTime, MediPackBarcode, MediPackStatus;
    RadioGroup radioGroup;
    RadioButton radioButton;
    int statu, radioId;
    String intype, pinIntype;
    Button search;
    Button collect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_oout);

        mediPackList = new ArrayList<>();
        myHelper = new DataBaseHelpe(this);
        med = new MediPackClient();

        PatientFisrtName = findViewById(R.id.name);
        PatientLastName = findViewById(R.id.surnname);
        PatientRSA = findViewById(R.id.idNumber);
        PatientCellphone = findViewById(R.id.cellphone);
        MediPackDueDateTime = findViewById(R.id.duedate);
        MediPackBarcode = findViewById(R.id.nhi);
        MediPackStatus = findViewById(R.id.status);
        ScannedInDateTime = findViewById(R.id.capaturedDate);
        radioButton = findViewById(radioId);
        radioGroup = findViewById(R.id.radioGroup);
        search = findViewById(R.id.search);
        inputType1 = findViewById(R.id.input1);
        edPin = findViewById(R.id.input2);
        collect = findViewById(R.id.collect);

        radioId = radioGroup.getCheckedRadioButtonId();

        //Getting the intent from the search person activity
        Intent intent = getIntent();
        med = (MediPackClient) intent.getSerializableExtra("object_of_medi");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ConstantMethods.validateTime() == true)
                {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(ScanOoutActivity.this, "Please check one of the radio buttons", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        if (radioButton.getText().equals("Pin")) {
                            intype = inputType1.getText().toString();
                            pinIntype = edPin.getText().toString();
                            validateInputedUserData();
                        } else {
                            intype = inputType1.getText().toString();
                            validateIDInputFields();

                            //Checking the length of the id
                            int idLength = Integer.parseInt(String.valueOf(intype.length()));

                            if (idLength == 15)
                            {
                                //Substring the id
                                String correctedId = intype.substring(1, 14);
                                intype = correctedId;
                                mediPackStatus(intype);
                            }
                            else if (idLength == 13) {
                                mediPackStatus(intype);
                            }
                            else
                            {
                                closeKeyboard();
                                inputType1.setError("ID Number not valid");
                                inputType1.setText("");
                                return;
                            }
                        }
                    }
                } else {
                    timeoutAlert();
                }
            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConstantMethods.validateTime() == true) {
                    if (med.getPatientRSA().equalsIgnoreCase(PatientRSA.getText().toString())) {

                        if (med.getMediPackStatusId() == 2) {
                            int NewStatus = 3;
                            int dirtyFlag = 2;

                            //current date
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = df.format(c.getTime());

                            myHelper.UpdateCollectStatus(NewStatus, intype, date, dirtyFlag);
                            clearingInputFields();

                            Toast.makeText(ScanOoutActivity.this, "Parcel successfully scanned out", Toast.LENGTH_LONG).show();
                        } else {
                            clearingInputFields();
                            Toast.makeText(ScanOoutActivity.this, "Parcel unsuccessfully scanned out", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    timeoutAlert();
                }
            }
        });
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //Inditify the check radio button
    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        if (radioButton.getText().equals("Pin")) {
            inputType1.setVisibility(View.VISIBLE);
            edPin.setVisibility(View.VISIBLE);
            inputType1.setHint("Enter Phone number");
            edPin.setHint("Enter Pin");
        } else {
            inputType1.setHint("Enter ID Number");
            inputType1.setVisibility(View.VISIBLE);
            edPin.setVisibility(View.INVISIBLE);
        }
    }


    public void validateIDInputFields() {
        if (intype.isEmpty()) {
            inputType1.setError("Input field cannot be empty");
            return;
        }
    }

    public void validateInputedUserData() {
        if (intype.isEmpty()) {
            inputType1.setError("Input field cannot be empty");
            return;
        }

        if (pinIntype.isEmpty()) {
            edPin.setError("Input field cannot be empty");
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.search) {
            Intent intent = new Intent(ScanOoutActivity.this, SearchPatientActivity.class);
            startActivity(intent);
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scanout, menu);
        return true;
    }

    //Setting user info and Updating the status message based on the medi pack id
    public void mediPackStatus(String id) {

        med = myHelper.searchIdORPIin(id);
        if (med != null)
        {
            closeKeyboard();
            inputType1.setText("");
            PatientFisrtName.setText(med.getPatientFisrtName());
            PatientLastName.setText(med.getPatientLastName());
            PatientRSA.setText(med.getPatientRSA());
            PatientCellphone.setText(med.getPatientCellphone());
            ScannedInDateTime.setText(med.getScannedInDateTime());
            MediPackBarcode.setText(med.getMediPackBarcode());
            MediPackDueDateTime.setText(med.getMediPackDueDateTime());

            //Getting the medipackstatus id from the database
            statu = med.getMediPackStatusId();

            if (statu == 0) {
                MediPackStatus.setText("Uploaded");

            } else if (statu == 1) {
                MediPackStatus.setText("Booking  for scanning ");

            } else if (statu == 2) {
                MediPackStatus.setText(" Scanned In");

            } else if (statu == 3) {
                MediPackStatus.setText("Scanned Out Collected ");
                collect.setVisibility(View.INVISIBLE);

            } else if (statu == 4) {
                MediPackStatus.setText("Collected by Patient With Assistance From Admin");
            } else if (statu == 5) {
                MediPackStatus.setText("Medication Returned Due to Non Collections");
            }
        } else {
            closeKeyboard();
            Toast.makeText(ScanOoutActivity.this, "No record found", Toast.LENGTH_LONG).show();
        }
    }

    public void clearingInputFields()
    {
        PatientFisrtName.setText(" ");
        PatientLastName.setText(" ");
        PatientRSA.setText(" ");
        PatientCellphone.setText(" ");
        ScannedInDateTime.setText(" ");
        MediPackBarcode.setText(" ");
        MediPackDueDateTime.setText(" ");
        MediPackStatus.setText("");
    }

    //Method to check if the users time has not expired
    public void timeoutAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScanOoutActivity.this);
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



