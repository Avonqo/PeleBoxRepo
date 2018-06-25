package com.example.aviwe.pelebox.utils;

import com.example.aviwe.pelebox.MainActivity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConstantMethods
{
    public static Boolean validateTime() {

        boolean valid = false;
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String d1 = df.format(c.getTime());
        Date currentDate = null;

        //change a string to a datetime format
        String timeoutDb = MainActivity.newTimeout;
        try {

            if (timeoutDb != null)
            {
                if (MainActivity.ckRemember.isChecked())
                {
                    Date d = df.parse(timeoutDb);
                    currentDate = df.parse(d1);
                    c.setTime(d);
                    c.add(Calendar.MINUTE, 1);

                    String newTime = df.format(c.getTime());

                    d=df.parse(newTime);

                    if (d.getTime() > currentDate.getTime()) {

                        valid=true;
                    }
                }
                else
                {
                    Date d= df.parse(timeoutDb);
                    currentDate = df.parse(d1);

                    if (d.getTime() > currentDate.getTime()) {

                        valid=true;
                    }
                }
            }

            else
            {
                valid = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return valid;
    }

}
