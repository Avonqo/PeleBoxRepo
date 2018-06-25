package com.example.aviwe.pelebox;

import android.app.SearchManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aviwe.pelebox.Scanin.ScanInParcelActivity;
import com.example.aviwe.pelebox.Scanout.ScanOoutActivity;
import com.example.aviwe.pelebox.checkInternetConnection.ConnectionDetector;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import com.example.aviwe.pelebox.report.CollectedParcelActivity;
import com.example.aviwe.pelebox.report.ScanoutExpiredActivity;
import com.example.aviwe.pelebox.report.ScanoutReadyCollectionActivity;
import com.example.aviwe.pelebox.search_parcel.SearchPatientActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class MediPackClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MediPackClientsAdapter.MediPackClientsAdapterListener
{
    private SearchView searchView;
    private RecyclerView mRecyclerView;
    private int JOB_ID=100;

    Context context;
    TextView namesurname,email;
    String user_email,token;
    int correctUserId;
    JobScheduler jobScheduler;
    JobInfo jobInfo;
    ConnectionDetector connectionDetector;
    DataBaseHelpe myHelper;
    ArrayList<MediPackClient> mediPackList;
    MediPackClientsAdapter mediAdapterObj;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_pack_client);
        context = getBaseContext();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.medCycle);

        mediAdapterObj = new MediPackClientsAdapter(MediPackClientActivity.this,mediPackList,  this);
        connectionDetector = new ConnectionDetector(this);

        ///Getting the intent from MainActivity
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            user_email = extras.getString("username");
            token = extras.getString("TokenLocal");
            correctUserId = extras.getInt("userId");
        }

        if (user_email!= null) {
            if (connectionDetector.isNetworkAvailable()) {
                costructorJob();
                jobScheduler.schedule(jobInfo);
            } else {
                Toast.makeText(context, " Not connected", Toast.LENGTH_LONG).show();
            }

        }

        //shared preference
        final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("token", token);
        editor.apply();

        //Navigator controller
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Navigator header
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headView = navigationView.getHeaderView(0);

        //Finding email and name fields on the navigation drawer
         namesurname =headView. findViewById(R.id.txtNameSurname);
         email = headView.findViewById(R.id.txtEmail);

        //Getting an intent email
        Intent intent = getIntent();
        user_email = intent.getStringExtra("Email");

        //Initializing the databasehelper class
        myHelper = new DataBaseHelpe(this);

        //Setting the email,name and surname on the navigator header
        namesurname.setText( MainActivity.user_name +" "+ MainActivity.user_surname);
        email.setText( MainActivity.uEmail);

        //Recycler view testing
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        myrecyclerview();

    }

    //Recycler view method
    public void myrecyclerview()
    {
        mediPackList = new ArrayList<>();
        mediPackList = myHelper.getAllMediPack();

        Button medPackTotal = findViewById(R.id.btnCount);
        int count = 0;

        //Counting how may medi pack are on the list
        for(MediPackClient medi : mediPackList)
        {
            count++;
        }

        //Setting the total number of medi pack on the list
        medPackTotal.setText(String.valueOf(count));

        mediAdapterObj = new MediPackClientsAdapter(MediPackClientActivity.this,mediPackList,  this);
        mediAdapterObj.notifyDataSetChanged();
        mRecyclerView.setAdapter(mediAdapterObj);
    }

    private void  costructorJob()
    {
        JobInfo.Builder builder= new JobInfo.Builder(JOB_ID,new ComponentName(this,MyServices.class));
        builder.setPeriodic(20000)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);
        jobInfo=builder.build();
        jobScheduler=(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.medi_pack_client, menu);

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

                mediAdapterObj.getFilter().filter(query);
                closeKeyboard();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mediAdapterObj.getFilter().filter(query);

                return false;
            }

        });
        return true;

    }

    //Method to close the keyboard
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_asc)
        {
            mRecyclerView = findViewById(R.id.medCycle);

            Collections.sort(mediPackList, new Comparator<MediPackClient>() {
                @Override
                public int compare(MediPackClient mediPackClient, MediPackClient t1) {
                    return mediPackClient.getPatientLastName().compareTo(t1.getPatientLastName());
                }
            });
        }
        else if (id == R.id.action_desc)
        {
            mRecyclerView = findViewById(R.id.medCycle);
            Collections.reverse(mediPackList);
        }
        else if (id == R.id.refresh)
        {
            //Toast.makeText(context, " refreshing", Toast.LENGTH_SHORT).show();
            myrecyclerview();
        }

        mediAdapterObj = new MediPackClientsAdapter(MediPackClientActivity.this,mediPackList,  this);
        mRecyclerView.setAdapter(mediAdapterObj);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Parcel_list)
        {

        }
        else if (id == R.id.Scan_in)
        {
            Intent i = new Intent(MediPackClientActivity.this,ScanInParcelActivity.class);
            startActivity(i);
        }
        else if (id == R.id.Scan_out)
        {
            Intent i = new Intent(MediPackClientActivity.this, ScanOoutActivity.class);
            startActivity(i);
        }
        else if (id == R.id.ready_collection)
        {
            Intent i = new Intent(MediPackClientActivity.this, ScanoutReadyCollectionActivity.class);
            startActivity(i);
        }
        else if (id == R.id.expired_collection)
        {
            Intent i = new Intent(MediPackClientActivity.this, ScanoutExpiredActivity.class);
            startActivity(i);
        }
        else if(id == R.id.nav_logout)
        {
          if (MainActivity.loginType.equalsIgnoreCase("local"))
          {
              Intent intent = new Intent(MediPackClientActivity.this,MainActivity.class);
              startActivity(intent);
          }
          else
          {
              LogOUT(MainActivity.newtoken, String.valueOf(correctUserId));
          }
        }
        else if(id == R.id.nav_searchparcel)
        {
            Intent i = new Intent(MediPackClientActivity.this, SearchPatientActivity.class);
            startActivity(i);
        }
        else if(id == R.id.scannedout_collection)
        {
            Intent i = new Intent(MediPackClientActivity.this, CollectedParcelActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public  void LogOUT(final String token , final String userId)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://medipackwebapi.azurewebsites.net/Medipack/Logout",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Token", token);
                params.put("UserId",userId);

                return params;
            }
        };

        MySingleton.getmInstance(MediPackClientActivity.this).addToRequestQue(stringRequest);
    }


    @Override
    public void onContactSelected(MediPackClient mediPackClient) {
        Intent intent = new Intent(MediPackClientActivity.this, PatientInformationActivity.class);
        intent.putExtra("object_of_medi", mediPackClient);
        startActivity(intent);
    }

}
