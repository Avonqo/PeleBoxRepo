package com.example.aviwe.pelebox;

import android.os.AsyncTask;

/**
 * Created by AVIWE on 2018/03/13.
 */

public class MJobExecuter extends AsyncTask<Void,Void,String> {


    @Override
    protected String doInBackground(Void... voids) {
        return " Background long running.......";
    }
}
