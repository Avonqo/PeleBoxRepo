package com.example.aviwe.pelebox.search_parcel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.aviwe.pelebox.MainActivity;
import com.example.aviwe.pelebox.MediPackClientsAdapter;
import com.example.aviwe.pelebox.R;
import com.example.aviwe.pelebox.DataBaseHelpe;
import com.example.aviwe.pelebox.ScanoutByAssistantActivity;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import com.example.aviwe.pelebox.pojos.UserClient;
import com.example.aviwe.pelebox.utils.ConstantMethods;
import com.example.aviwe.pelebox.utils.RecyclerItemClickListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SearchPatientActivity extends AppCompatActivity {

    private RadioGroup searchGroup;
    private DataBaseHelpe helper;
    private RecyclerView mRecyclerView;

    ArrayList<MediPackClient> mediPackList;
    MediPackClientsAdapter adapter;
    MediPackClient med;

    RadioButton radioButton1;
    String strSearch;
    EditText edtParcelSearch;
    Button btnSearchParcel;
    int radioId1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);

        adapter = new MediPackClientsAdapter(mediPackList);
        med = new MediPackClient();

        edtParcelSearch = findViewById(R.id.input);
        btnSearchParcel = findViewById(R.id.search);
        searchGroup = findViewById(R.id.radioGroup);

        //Initiatiating the database helper and recycler view
        helper = new DataBaseHelpe(this);
        mRecyclerView = findViewById(R.id.parcel_ready_for_collection);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        //Intitiating the array and getting all the records
        mediPackList = new ArrayList<>();
        mediPackList = helper.getAllMediPackToBeCollected();
        getAdapter(mediPackList);

        radioId1 = searchGroup.getCheckedRadioButtonId();
        radioButton1 = findViewById(radioId1);

        //search button function/filtering
        btnSearchParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (ConstantMethods.validateTime() == true)
                {
                    strSearch = edtParcelSearch.getText().toString();
                    try
                    {
                        if (radioButton1.getText().equals("Surname"))
                        {
                            if (TextUtils.isEmpty(strSearch))
                            {
                                edtParcelSearch.setError("Please enter the surname");
                                return;
                            }
                            else
                            {
                                closeKeyboard();
                                mediPackList = helper.searchBySurname(strSearch);
                                getAdapter(mediPackList);
                            }
                        }
                        else if (radioButton1.getText().equals("Id Number"))
                        {
                            if (TextUtils.isEmpty(strSearch))
                            {
                                edtParcelSearch.setError("Please enter the identity number");
                                return;
                            }
                            else
                                {
                                closeKeyboard();
                                mediPackList = helper.searchById(strSearch);
                                getAdapter(mediPackList);
                            }
                        }
                        else if (radioButton1.getText().equals("Date of Birth")) {
                            if (TextUtils.isEmpty(strSearch)) {
                                edtParcelSearch.setError("Please enter the date of birth");
                                return;
                            }
                            else
                                {
                                closeKeyboard();

                                if(strSearch.length() == 6)
                                {
                                    mediPackList = helper.searchBydateofbirth(strSearch);
                                    getAdapter(mediPackList);
                                }
                                else {
                                    edtParcelSearch.setError("Please enter 6 digits to search");
                                }
                            }

                        }
                        else if (radioButton1.getText().equals("Cell Number"))
                        {
                            if (TextUtils.isEmpty(strSearch)) {
                                edtParcelSearch.setError("PLease enter the Cell Number");
                                return;
                            }
                            else
                                {
                                closeKeyboard();
                                mediPackList = helper.searchByCellNumber(strSearch);
                                getAdapter(mediPackList);
                            }
                        }

                        edtParcelSearch.setText("");
                    }
                    catch (Exception e)
                    {
                         closeKeyboard();
                        Toast.makeText(getBaseContext(), "Please select the option given", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchPatientActivity.this);
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

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MediPackClient medi = mediPackList.get(position);

                Intent intent = new Intent(SearchPatientActivity.this, ScanoutByAssistantActivity.class);
                intent.putExtra("object_of_medi", medi);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }) {
        });
    }

    public void getAdapter(ArrayList arrayList)
    {
        if (arrayList.size() == 0)
        {
            arrayList = helper.getAllMediPackToBeCollected();
            adapter = new MediPackClientsAdapter(arrayList);
            adapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(adapter);
            Toast.makeText(SearchPatientActivity.this, "No record found", Toast.LENGTH_LONG).show();
        }
        else {
            adapter = new MediPackClientsAdapter(arrayList);
            adapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(adapter);
        }
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //Checking the checked gender radio button
    public void checkButton(View view)
    {
        radioId1 = searchGroup.getCheckedRadioButtonId();
        radioButton1 = findViewById(radioId1);

      if(radioButton1.getText().equals("Surname"))
        {
            edtParcelSearch.setHint("Enter Surname");
            edtParcelSearch.setInputType(InputType.TYPE_CLASS_TEXT);
            edtParcelSearch.setText("");
        }
        else if(radioButton1.getText().equals("Date of Birth"))
        {
            edtParcelSearch.setHint("YY-MM-DD");
            edtParcelSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtParcelSearch.setText("");
        }
        else if(radioButton1.getText().equals("Cell Number"))
        {
            edtParcelSearch.setHint("Enter Cell number");
            edtParcelSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtParcelSearch.setText("");
        }
        else if(radioButton1.getText().equals("Id Number"))
        {
            edtParcelSearch.setHint("Enter ID Number");
            edtParcelSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtParcelSearch.setText("");
        }
    }

}
