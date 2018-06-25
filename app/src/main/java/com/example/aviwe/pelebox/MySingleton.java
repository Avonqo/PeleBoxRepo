package com.example.aviwe.pelebox;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Tharollo on 2018/02/27.
 */

public class MySingleton {

    private static  MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context  mContext;

    public MySingleton(Context context) {
        requestQueue = requestQueue;
        mContext = context;
    }

   private RequestQueue getRequestQueue()
   {
       if(requestQueue == null){
           requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
       }
       return requestQueue;
   }

   public static synchronized MySingleton getmInstance(Context context){
        if (mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
   }

   public void addToRequestQue(Request<?> request){
       getRequestQueue().add(request);
   }
    
}
