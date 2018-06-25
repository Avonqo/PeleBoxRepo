package com.example.aviwe.pelebox;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.aviwe.pelebox.checkInternetConnection.ConnectionDetector;
import com.example.aviwe.pelebox.pojos.Confirmation;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by AVIWE on 2018/03/09.
 */

public class MyServices extends android.app.job.JobService
{
    MJobExecuter mJobExecuter;
    static String tokenVal = null;
    int number=1;
    ConnectionDetector connectionDetector;
    final DataBaseHelpe myHelper = new DataBaseHelpe(this);
    String apiLink = "http://medipackwebapi.azurewebsites.net/Medipack/";

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final android.app.job.JobParameters jobParameters) {
        jobFinished(jobParameters,false);

        // check internet connection
        mJobExecuter = new MJobExecuter()
        {
            @Override
            protected void onPostExecute(String s) {

                connectionDetector = new ConnectionDetector(MyServices.this);
                final String MY_PREFS_NAME = "MyPrefsFile";
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                tokenVal =MainActivity.newtoken;

            refreshRecyclerview();
                if (tokenVal!=null) {
                    if (connectionDetector.isNetworkAvailable()) {
                       getcloudData();
                    } else {
                    }
                }
                updateCloudData();
                jobFinished(jobParameters,false);
            }
        };

        mJobExecuter.execute();
        return true;
    }

    @Override
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        mJobExecuter.cancel(true);

        return false;
    }

    public  void getcloudData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiLink +"GetCloudData/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray medipackArray = jsonObject.getJSONArray("MediPackList");

                            ArrayList<Confirmation> confirmationList = new ArrayList<>();

                            Confirmation conf;
                            for (int i = 0; i < medipackArray.length(); i++) {

                                JSONObject medipackObject = medipackArray.getJSONObject(i);

                                //MediPackClient pojo object
                                MediPackClient medi = new MediPackClient();

                                medi.setMediPackId(medipackObject.getInt("MediPackId"));
                                medi.setPatientFisrtName(medipackObject.getString("PatientFirstName"));
                                medi.setPatientLastName(medipackObject.getString("PatientLastName"));
                                medi.setPatientCellphone(medipackObject.getString("PatientCellphone"));
                                medi.setMediPackBarcode(medipackObject.getString("MediPackBarcode"));
                                medi.setPatientRSA(medipackObject.getString("PatientRSA"));
                                medi.setManifestNumber(medipackObject.getString("ManifestNumber"));
                                medi.setDeviceId(medipackObject.getInt("DeviceId"));
                                medi.setInUserId(medipackObject.getInt("InUserId"));
                                medi.setMediPackDueDateTime(medipackObject.getString("MediPackDueDateTime"));
                                medi.setMediPackStatusId(medipackObject.getInt("MediPackStatusId"));
                                medi.setDirtyFlag(medipackObject.getInt("DirtyFlag"));

                                int myid = medi.getMediPackId();

                                switch (medi.getDirtyFlag()) {
                                    case 1:
                                        MediPackClient mediPackClient = myHelper.getMedipackDetails(myid);

                                        if (mediPackClient != null) {
                                            myHelper.UpdateMedipackData(medi);
                                        }
                                        else {
                                            String names= medi.getPatientLastName();
                                            names.toLowerCase();
                                            myHelper.addDataFromCloud(medi);
                                        }
                                        conf = new Confirmation(myid, 0);
                                        confirmationList.add(conf);
                                        break;
                                    case 2:
                                        myHelper.UpdateMedipackData(medi);

                                        conf = new Confirmation(myid, 0);
                                        confirmationList.add(conf);
                                        break;
                                    default:
                                        conf = new Confirmation(myid, medi.getDirtyFlag());
                                        confirmationList.add(conf);
                                        myHelper.DeleteMediPackData(medi.getMediPackId());
                                        break;
                                }
                            }

                            Gson gson = new Gson();
                            String jsonCartList = gson.toJson(confirmationList);
                            sendConfirmationToCloud(jsonCartList);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
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

                params.put("Token", tokenVal);
                params.put("DeviceId", "1");
                params.put("UserId", "1");

                return params;
            }
        };

        MySingleton.getmInstance(MyServices.this).addToRequestQue(stringRequest);
    }

    public void sendConfirmationToCloud( final String jsonCartList)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiLink +"SendConfirmationToCloud/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

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

                params.put("Token", tokenVal);
                Log.d("token 2",tokenVal);
                params.put("DeviceId", "1");
                params.put("UserId", "1");
                params.put("confList ", jsonCartList);

                return params;
            }
        };

        MySingleton.getmInstance(MyServices.this).addToRequestQue(stringRequest);
    }

    public  void updateCloudData()
    {
        final ArrayList<MediPackClient> updateList  = GetPendingMedipackData();
        Gson gson = new Gson();
        final String penidngMediPackList  = gson.toJson(updateList);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiLink +"UpdateCloudData/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.equals("true"))
                        {
                            //update local database
                            for(int i=0; i< updateList.size();i++)
                            {
                                myHelper.UpdateDirtyFlag(updateList.get(i).getMediPackId());
                            }
                        }
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

                params.put("Token", tokenVal);
                params.put("DeviceId", "1");
                params.put("UserId", "1");
                params.put("MedipackList",penidngMediPackList);

                return params;
            }
        };

        MySingleton.getmInstance(MyServices.this).addToRequestQue(stringRequest);
    }

    public ArrayList<MediPackClient> GetPendingMedipackData()
    {
        ArrayList<MediPackClient> updateList=new ArrayList<MediPackClient>();
        updateList= myHelper.getAllMediPackCollectedForUpDate();

        return updateList;
    }

    public void refreshRecyclerview()
    {
        ArrayList<MediPackClient> mediPackClients=myHelper.getAllMediPack();
    }

}
