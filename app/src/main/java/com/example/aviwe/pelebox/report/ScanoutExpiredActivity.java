package com.example.aviwe.pelebox.report;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import com.example.aviwe.pelebox.MediPackClientsAdapter;
import com.example.aviwe.pelebox.R;
import com.example.aviwe.pelebox.DataBaseHelpe;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScanoutExpiredActivity extends AppCompatActivity implements MediPackClientsAdapter.MediPackClientsAdapterListener
{
    private RadioGroup ageRadioGroup,genderRadioGroup;
    private RadioButton radioButton;
    private DataBaseHelpe helper;
    private Button btnSearchData,btnCount,btnGender;
    private SearchView searchView;
    private RecyclerView mRecyclerView;
    ArrayList<MediPackClient> mediPackList,listID,expiredList;
    MediPackClientsAdapter adapter;
    MediPackClient med;
    int count,countall ,year,convert,addYear2,patientAge,counter,radioid,convertYear;
    String patientId,firstNumber,addYear1,mediPackPatientYear,checkingid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanout_expired);

        adapter = new MediPackClientsAdapter(mediPackList);
        helper = new DataBaseHelpe(this);
        med = new MediPackClient();
        mediPackList = new ArrayList<>();
        expiredList = new ArrayList<>();
        listID = new ArrayList<>();

        btnSearchData = findViewById(R.id.btnSearchData);
        btnCount = findViewById(R.id.btnCount);
        btnGender = findViewById(R.id.btnSearchGender);
        mRecyclerView = findViewById(R.id.parcel_ready_for_collection);

        mediPackList = helper.getAllMediPackToBeCollected();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        //get Current time
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String d1 = df.format(c.getTime());

        for(MediPackClient med : mediPackList)
        {
            Date d = new Date();
            Date currentDate = null;
            String b = med.getMediPackDueDateTime();

            try {
                currentDate = df.parse(d1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                d = df.parse(b);
            } catch (ParseException e) {
                e.printStackTrace();
            }

                if (d.getTime() < currentDate.getTime())
                {
                    countall++;
                    expiredList.add(med);
                    getAdapter(expiredList);

            }

        }
        btnCount.setText(String.valueOf(countall));

        ageRadioGroup = findViewById(R.id.rgAge);
        genderRadioGroup = findViewById(R.id.rgGender);
        radioid = ageRadioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioid);


        btnSearchData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(radioButton.getText().equals("0 to 17"))
                {
                    listID.clear();
                    count = 0;
                        for(MediPackClient med : expiredList)
                        {
                            //Getting the id from the database
                            patientId = med.getPatientRSA();

                            //Getting the first 2 digit of the id
                            checkingid = patientId.substring(0, 2);

                            //Getting the first digit of the id
                            firstNumber = patientId.substring(0, 1);

                            //Converting string id to integer

                            if(convert == 0 || convert == 1 )
                            {
                                addYear1 = "20";
                                mediPackPatientYear = addYear1 + checkingid;

                                convertYear = Integer.parseInt(mediPackPatientYear);

                                patientAge  = getCurrentYear() - convertYear;

                                if(patientAge >=0 || patientAge <= 17)
                                {
                                    count++;
                                    listID.add(med);
                                }
                            }
                         }
                    getAdapter(listID);
                    btnCount.setText(String.valueOf(count));
                }
                else if(radioButton.getText().equals("18 to 35"))
                {
                    listID.clear();
                    counter = 0;
                    for(MediPackClient med : expiredList)
                    {
                        //Getting the id from the database
                        patientId = med.getPatientRSA();

                        //Getting the first 2 digit of the id
                        checkingid = patientId.substring(0, 2);

                        //Getting the first digit of the id
                        firstNumber = patientId.substring(0, 1);

                        //Converting string id to integer

                        if (convert == 0 || convert == 1)
                        {
                            addYear1 = "20";
                            mediPackPatientYear = addYear1 + checkingid;
                            convertYear = Integer.parseInt(mediPackPatientYear);
                            patientAge = getCurrentYear() - convertYear;

                            if (patientAge >= 18 && patientAge <= 35)
                            {
                                counter++;
                                listID.add(med);
                            }
                        }
                        else if(convert >=2)
                        {
                            addYear2 = 19;
                            mediPackPatientYear = addYear2 + checkingid;
                            convertYear = Integer.parseInt(mediPackPatientYear);
                            int patientAge = getCurrentYear() - convertYear;

                            if(patientAge >= 18 && patientAge <= 35)
                            {
                                counter++;
                               listID.add(med);
                            }
                        }
                        count = counter;
                    }
                    getAdapter(listID);
                    btnCount.setText(String.valueOf(count));
                }
                else if(radioButton.getText().equals("36 to 65"))
                {
                    listID.clear();
                    counter = 0;
                    for (MediPackClient med : expiredList)
                    {
                        //Getting the id from the database
                        patientId = med.getPatientRSA();

                        //Getting the first 2 digit of the id
                        checkingid = patientId.substring(0, 2);

                        //Getting the first digit of the id
                        firstNumber = patientId.substring(0, 1);

                        //Converting string id to integer
                        convert = Integer.parseInt(firstNumber);

                        if (convert >= 2 )
                        {
                            addYear1 = "19";
                            mediPackPatientYear = addYear1 + checkingid;
                            convertYear = Integer.parseInt(mediPackPatientYear);
                            patientAge = getCurrentYear() - convertYear;

                            if (patientAge >= 36 && patientAge <= 65)
                            {
                                counter++;
                                listID.add(med);
                            }
                        }
                        count = counter;
                    }

                    getAdapter(listID);
                    btnCount.setText(String.valueOf(count));
                }
                else if(radioButton.getText().equals("Above 65"))
                {
                    listID.clear();
                    counter = 0;
                    for (MediPackClient med : expiredList)
                    {
                        //Getting the id from the database
                        patientId = med.getPatientRSA();

                        //Getting the first 2 digit of the id
                        checkingid = patientId.substring(0, 2);

                        //Getting the first digit of the id
                        firstNumber = patientId.substring(0, 1);

                        //Converting string id to integer
                        convert = Integer.parseInt(firstNumber);

                        if (convert >= 2 )
                        {
                            addYear1 = "19";
                            mediPackPatientYear = addYear1 + checkingid;
                            convertYear = Integer.parseInt(mediPackPatientYear);
                            patientAge = getCurrentYear() - convertYear;
                            if (patientAge >= 66 )
                            {
                                counter++;
                                listID.add(med);
                            }
                        }
                        count = counter;
                    }
                    getAdapter(listID);
                    btnCount.setText(String.valueOf(count));

                }
                else if(radioButton.getText().equals("All"))
                {
                    //expiredList.clear();
                    counter = 0;

                    for(MediPackClient user : expiredList)
                    {
                        counter++;
                    }
                    btnCount.setText(String.valueOf(counter));
                    getAdapter(expiredList);
                }
            }
        });

       //Sort by gender functionality
        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(radioButton.getText().equals("Female"))
                {
                    listID.clear();
                    counter = 0;
                    for (MediPackClient med : expiredList)
                    {
                        //Getting the id from the database
                        patientId = med.getPatientRSA();

                        //Getting the 7th didgit from the id
                        checkingid = patientId.substring(6,7);

                        //Convert String to int
                        convert = Integer.parseInt(checkingid);

                       if (convert >= 0 && convert <= 4)
                        {
                           counter++;
                           listID.add(med);
                        }
                   }
                    getAdapter(listID);
                    btnCount.setText(String.valueOf(counter));
                }
                else if(radioButton.getText().equals("Male"))
                {
                    listID.clear();
                    counter = 0;
                    for (MediPackClient med : expiredList)
                    {
                        patientId = med.getPatientRSA();

                        //Cutting the is to get the first two numbers
                        checkingid = patientId.substring(6,7);

                        //Getting the first number from the database
                        convert = Integer.parseInt(checkingid);
                        //Log.d("sara", String.valueOf(convert));
                        if (convert >= 5 && convert <= 9)
                        {
                            counter++;
                            listID.add(med);
                        }
                    }
                    getAdapter(listID);
                    btnCount.setText(String.valueOf(counter));
                }
                else if(radioButton.getText().equals("All"))
                {
                    counter = 0;

                    for(MediPackClient user : expiredList)
                    {
                        counter++;
                    }
                    btnCount.setText(String.valueOf(counter));
                    getAdapter(expiredList);
                }
            }
        });
    }
    public void checkButton(View v)
    {
         radioid = ageRadioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioid);

        if(radioButton.getText().equals("0 to 17")) {}
        else if(radioButton.getText().equals("18 to 35")) {}
        else if(radioButton.getText().equals("36 to 65")) {}
        else if(radioButton.getText().equals("Above 65")) {}
        else if(radioButton.getText().equals("All")) {}
    }

    public int getCurrentYear()
    {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        return year;
    }

    public void getAdapter(ArrayList arrayList)
    {
        adapter = new MediPackClientsAdapter(arrayList);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }

    public void checkedGender(View view)
    {
       radioid= genderRadioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioid);
        if(radioButton.getText().equals("Male")) {}
        else if(radioButton.getText().equals("Female")) {}
        else if(radioButton.getText().equals("All")) {}
    }


    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onContactSelected(MediPackClient mediPackClient) {

    }
}
