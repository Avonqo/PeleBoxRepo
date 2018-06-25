package com.example.aviwe.pelebox.Scanin;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.aviwe.pelebox.MainActivity;
import com.example.aviwe.pelebox.R;
import com.example.aviwe.pelebox.DataBaseHelpe;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import com.example.aviwe.pelebox.pojos.UserClient;
import com.example.aviwe.pelebox.utils.ConstantMethods;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ScanInParcelActivity extends AppCompatActivity
{
    private DataBaseHelpe helper;
    private EditText edBarcode;
    private MediPackClient med;
    private ScannedInAdapter scannedInAdapter;
    private RecyclerView mRecyclerView;
    private int status;
    private Button btnAcceptAll;
    private Timer timer;
    String barcode, changedBarcode;
    ArrayList<MediPackClient> medList;
    Context context;
    boolean isavailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_testing);

        med = new MediPackClient();
        helper = new DataBaseHelpe(this);
        medList = new ArrayList<>();

        context = getBaseContext();

        edBarcode = findViewById(R.id.edtBarcode);
        mRecyclerView = findViewById(R.id.scanInCycle);
        btnAcceptAll = findViewById(R.id.bntAcceptAll);

        scannedInAdapter = new ScannedInAdapter(medList, ScanInParcelActivity.this);
        scannedInAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(scannedInAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        //Textwatcher barcode scan in functionality
        edBarcode.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ScanInParcelActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });

                        try{
                            Thread.sleep(100);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        ScanInParcelActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myBarcode();
                                edBarcode.setText("");
                            }
                        });

                    }
                }, 400);

                }
        });

        //Accept all scan in medi pack functionality
        btnAcceptAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ConstantMethods.validateTime() == true) {

                            status = 2;
                            int dirtyFlag = 2;

                            //Getting the current date the medi parecel was scanned in
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            String formattedDate = df.format(c.getTime());

                            //Getting the method to update the status and date to all the record
                            for (MediPackClient mec : medList)
                            {
                                helper.UpdateAllStatusOfScannedInMediPack(status, formattedDate, dirtyFlag,mec.getMediPackId());
                                Toast.makeText(ScanInParcelActivity.this, "All Parcel has been successful scanned", Toast.LENGTH_LONG).show();
                            }

                            medList.clear();
                            scannedInAdapter.notifyDataSetChanged();
                        }
                        else{
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ScanInParcelActivity.this);
                            builder.setTitle("Timeout Warning !");
                            builder.setMessage("Your time has expired .Please login again");
                            builder.setIcon(R.drawable.ic_warning_black_24dp);

                            builder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    UserClient userClient= new UserClient();
                                    userClient.setToken("");
                                    helper.UpdateUser(userClient);
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                            builder.show();
                        }
                    }

                });
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void myBarcode()
    {
        barcode = edBarcode.getText().toString();

        isavailable = false;
        //This is a NHI that starts with the * when scanned
        if(barcode.length() == 14)
        {
            //Checking if the barcode starts with NHI
            changedBarcode = barcode.substring(1, 4);

            if(changedBarcode.equalsIgnoreCase("NHI"))
            {
                String nhi=barcode.substring(1,13);

                //Searching if the barcode does exist on the database
                med = helper.getBarcodeParcel(nhi);
                scanInBarcodeFunctinality(nhi);

            }
            else
            {
                closeKeyboard();
                Toast.makeText(ScanInParcelActivity.this, " No such barcode  found, Please try", Toast.LENGTH_LONG).show();
            }
        }
        //This is a NHI that does not start with the * when scanned
        else if(barcode.length() == 12)
        {
            changedBarcode = barcode.substring(0, 3);
            if(changedBarcode.equalsIgnoreCase("NHI"))
            {
                med = helper.getBarcodeParcel(barcode);
                scanInBarcodeFunctinality(barcode);
            }
            else
            {
                closeKeyboard();
                Toast.makeText(ScanInParcelActivity.this, " No such barcode  found, Please try", Toast.LENGTH_LONG).show();
            }
        }

        return;

    }

    public void scanInBarcodeFunctinality(String scannedNHI)
    {
        if (med != null)
        {
            if(med.getMediPackStatusId() == 1)
            {
                for (MediPackClient m : medList)
                {
                    if (m.getMediPackBarcode().equalsIgnoreCase(scannedNHI)) {
                        isavailable = true;
                        break;
                    }
                }

                if (isavailable == false)
                {
                    medList.add(med);
                }
                else if (isavailable == true)
                {
                    closeKeyboard();
                    Toast.makeText(ScanInParcelActivity.this, " Parcel barcode has already been scanned in", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(ScanInParcelActivity.this, " Parcel has already being scanned", Toast.LENGTH_LONG).show();
            }
        }
        else if(changedBarcode.equalsIgnoreCase("NHI"))
        {
            for(MediPackClient m :medList ){
                if( m.getMediPackBarcode().equalsIgnoreCase(scannedNHI)) {
                    isavailable = true;
                    break;
                }
            }

            if(isavailable == false)
            {
                med = new MediPackClient("", "", "", scannedNHI, "", "", 0);
                medList.add(med);
            }
            else
            {
                closeKeyboard();
                Toast.makeText(ScanInParcelActivity.this, "barcode has already been scanned in for unknowm barcode", Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            closeKeyboard();
            Toast.makeText(ScanInParcelActivity.this, " No such barcode was found, Please try", Toast.LENGTH_LONG).show();
        }
        scannedInAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.scan_in, menu);
        return true;
    }

}

