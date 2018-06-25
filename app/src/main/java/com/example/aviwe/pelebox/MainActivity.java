package com.example.aviwe.pelebox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aviwe.pelebox.checkInternetConnection.ConnectionDetector;
import com.example.aviwe.pelebox.forgotPassword.RetrieveActivity;
import com.example.aviwe.pelebox.pojos.UserClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity implements TextWatcher,CompoundButton.OnCheckedChangeListener
{
    private EditText password,user_email;
    private Button btnLogin;
    private TextView requestPassword;

    private ProgressDialog dialog;
    static public CheckBox ckRemember;

    //DatabaseHelper Object
    DataBaseHelpe myHelper;

    //Object of the pojo
    UserClient userClient;

    JSONObject jsonObject;
    String userPassword,userEmail;
    boolean valid = true;

    public static String newtoken = null;
    public static String newTimeout = null;
    public static String user_name,user_surname,uEmail,uPassword,loginType;

    //Shared Preferences for remember me function
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";

    //Checking the network availabilty
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        connectionDetector=new ConnectionDetector(this);
        myHelper= new DataBaseHelpe(this);

        //Finding the variables
        ckRemember = findViewById(R.id.ckRememberM);
        requestPassword = findViewById(R.id.txtRequestPassword);
        password = findViewById(R.id.edPssword);
        user_email = findViewById(R.id.edEmail);
        btnLogin=findViewById(R.id.btnSearch);

        //Allow single line to the fields
        password.setSingleLine(true);
        user_email.setSingleLine(true);

        //Hidding the password
        password.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);


        //Progress Dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Signing in");
        dialog.setMessage("Please wait...");

        //Login button functions
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.show();
                userPassword= password.getText().toString();
                userEmail= user_email.getText().toString();

                if(validateInput() ==true)
                {
                    UserClient userClientVal = myHelper.verifyUser(userEmail, userPassword);
                    if (userClientVal != null)
                    {
                        //Get current date time
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String d1 = df.format(c.getTime());
                        Date date = null;
                        Date currentDate = null;

                        //change a string to a datetime format
                        String timeoutDb = userClientVal.getTimeout();

                            try
                            {
                                date = df.parse(timeoutDb);
                                currentDate = df.parse(d1);
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                            }

                        if (date.getTime() > currentDate.getTime())
                        {
                            newtoken = userClientVal.getToken();
                            user_name =userClientVal.getUserFirstName();
                            user_surname =userClientVal.getUserLastName();
                            uEmail=userClientVal.getUserEmail();
                            newTimeout = userClientVal.getTimeout();
                            loginType="local";

                            Intent intent = new Intent(MainActivity.this, MediPackClientActivity.class);
                            startActivity(intent);

                            dialog.dismiss();
                        }
                        else
                        {
                            if (connectionDetector.isNetworkAvailable())
                            {
                                LoginFromCloud();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, " no internet connection", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    else
                    {
                        if(connectionDetector.isNetworkAvailable())
                        {
                            LoginFromCloud();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, " no internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                closeKeyboard();
                dialog.dismiss();
            }
        });

        requestPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,RetrieveActivity.class);
                startActivity(intent);
            }
        });

        //Function for remember checkbox
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
        {
            ckRemember.setChecked(true);
        }
        else
        {
            ckRemember.setChecked(false);
        }

        user_email.setText(sharedPreferences.getString(KEY_USERNAME,""));
        user_email.addTextChangedListener(this);
        password.addTextChangedListener(this);
        ckRemember.setOnCheckedChangeListener(this);
    }

    //Method for closing the keyboard
    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void LoginFromCloud()
    {
        final String email= user_email.getText().toString();
        final String jpassword=password.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://medipackwebapi.azurewebsites.net/Medipack/Login/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            String stringValue = "\"Unauthorized access!\"";

                            if (response.equalsIgnoreCase(stringValue))
                            {
                                Toast.makeText(MainActivity.this, "Unauthorized access!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                jsonObject = new JSONObject(response);

                                //Getting the current date time and add 60 minutes to it
                                String f = "";
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                f = df.format(c.getTime());
                                Date d = null;
                                try
                                {
                                    d = df.parse(f);
                                }
                                catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                c.setTime(d);
                                c.add(Calendar.MINUTE, 1);
                                String newTime = df.format(c.getTime());

                                userClient = new UserClient(
                                        Integer.parseInt(jsonObject.getString("UserId")),
                                        jsonObject.getString("UserFirstName"),
                                        jsonObject.getString("UserLastName"),
                                        jsonObject.getString("UserPassword"),
                                        Integer.parseInt(jsonObject.getString("UserRoleId")),
                                        jsonObject.getString("UserEmail"),
                                        jsonObject.getString("Token"),
                                        newTime);

                                ArrayList<UserClient> users = myHelper.getAllUsers();

                                //Checking if local database has data
                                if (users.size() == 0)
                                {
                                    userClient.setUserPassword(userPassword);
                                    myHelper.addUserFromCloud(userClient);
                                }
                                else {
                                    for (UserClient userClients : users) {
                                        if (userClients.getUserclientId() == userClient.getUserclientId()) {
                                            userClient.setUserPassword(userPassword);
                                            myHelper.UpdateUser(userClient);
                                        }
                                    }
                                }

                                user_name = userClient.getUserFirstName();
                                user_surname = userClient.getUserLastName();
                                uEmail = userClient.getUserEmail();
                                uPassword = userClient.getUserPassword();

                                newtoken = jsonObject.getString("Token");
                                newTimeout = newTime;
                                loginType="cloud";

                                Intent intent = new Intent(MainActivity.this, MediPackClientActivity.class);
                                intent.putExtra("username", jsonObject.getString("UserEmail"));
                                intent.putExtra("token", jsonObject.getString("Token"));
                                intent.putExtra("userId", jsonObject.getInt("UserId"));
                                intent.putExtra("  loginType", " cloud");
                                startActivity(intent);
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("Response Error  ", String.valueOf(error));
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email",email );
                params.put("Password",jpassword );
                params.put("DeviceId", "1");
                params.put("Reme", "1");

                return params;
            }
        };

        MySingleton.getmInstance(MainActivity.this).addToRequestQue(stringRequest);
    }


    //Validation method for user inputed data/ Edit text
    public boolean validateInput()
    {
        String passwordHolder, emailHolder;

        passwordHolder = password.getText().toString();
        emailHolder = user_email.getText().toString();

        if (!emailHolder.isEmpty() )
        {
            if (emailValidator(emailHolder)) {
                String[] token = emailHolder.split("@");
                if (token[1].equalsIgnoreCase("technovera.co.za"))
                {
                    //validating if the password does not contain a space
                    for (int i = 0; i < passwordHolder.length(); i++) {
                        if (Character.isWhitespace(passwordHolder.charAt(i))) {
                            password.setError("Password must not contain spaces");
                            valid = false;
                        }
                    }
                }
                else {
                    user_email.setError("please enter correct domain email ");

                    valid = false;
                }
            } else {
                user_email.setError(" invalid email");
            }

            if (!passwordHolder.isEmpty()) {

            } else {

                password.setError(" Please eneter password");
            }
        }
        else
        {
            user_email.setError(" email is empty");
        }

        return valid;
    }


    //Method to save key values for password and password if the remember me check box is checked
    private void managePrefs(){
        if(ckRemember.isChecked()){
            editor.putString(KEY_USERNAME, user_email.getText().toString().trim());

            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_USERNAME);
            editor.apply();
        }
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }
}