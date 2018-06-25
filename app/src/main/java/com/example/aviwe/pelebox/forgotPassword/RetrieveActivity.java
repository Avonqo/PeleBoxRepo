package com.example.aviwe.pelebox.forgotPassword;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.example.aviwe.pelebox.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RetrieveActivity extends AppCompatActivity {

    EditText edittxt_email;
    Button btnRequestpassword;
    String emailTo,emailFrom,emailSubject,emailMessage,emailPattern;
    SendMail sendMail;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_retrieve);

        //progress dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Requesting Password");
        dialog.setMessage(" Sending the email..Please wait...");


        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                dialog.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);

        edittxt_email = (EditText)findViewById(R.id.txt_RequestPassword);
        btnRequestpassword = (Button)findViewById(R.id.btn_requestPassword);

        emailTo = "tharollo.moraba@technovera.co.za";
        emailFrom = edittxt_email.getText().toString().trim();
        emailSubject = "Request Password";
        emailMessage = "Dear Administrator.\n\n" +
                "Can you please help me with my login details" +"\n\n My email is: ";

        btnRequestpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();

                closeKeyboard();
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

    public  void validateEmail()
    {
        emailPattern =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        emailFrom = edittxt_email.getText().toString();

        Matcher matcher = Pattern.compile(emailPattern).matcher(emailFrom);

        if(emailFrom.length() > 0)
        {
            if (matcher.matches())
            {
                dialog.show();
                sendMail  = new SendMail(getApplicationContext(), emailTo, emailSubject , emailMessage+ emailFrom);
                sendMail.execute();
                edittxt_email.setText(" ");
                dialog.dismiss();
            }
            else
            {
                edittxt_email.setError("The email is invalid");
            }
        }
        else
        {
            edittxt_email.setError("Please enter the email");
        }

    }

}
