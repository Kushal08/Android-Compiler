package com.example.coed152.androcompile;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;

        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

public class SuccessActivity extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    private EditText etPassword;
    private LocationManager locationManager;
    TextView t1,t2,t3;
    Button button;
    String data,inputs,operation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another);

        Bundle bundle=this.getIntent().getExtras();
        data=bundle.get("code").toString();
        inputs=bundle.get("inputs").toString();
        operation =bundle.get("operation").toString();


        // Get Reference to variables
        etEmail = (EditText) findViewById(R.id.t11);
        etEmail.setText(data);

        etPassword = (EditText)findViewById(R.id.t12);




    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {

        // Get text from email and passord field
        final String email = etEmail.getText().toString();
        final String input1 = inputs;


        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(email,input1);

    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(SuccessActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                if(operation.equals("C")) {
                    url = new URL("http://10.42.0.1/admin/getcode.php");
                }
                else if(operation.equals("C++"))
                {
                    url = new URL("http://10.42.0.1/admin/getcode1.php");

                }
                else if(operation.equals("Python"))
                {
                    url = new URL("http://10.42.0.1/admin/getcode2.php");

                }
                else if(operation.equals("Java"))
                {
                    url = new URL("http://10.42.0.1/admin/getcode3.php");

                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", params[0])
                        .appendQueryParameter("input1", params[1])
                        ;
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            //Toast.makeText(SuccessActivity.this, result, Toast.LENGTH_LONG).show();
            etPassword.setText(result);

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                //Intent intent = new Intent(MainActivity.this,SuccessActivity.class);
                //startActivity(intent);
                //MainActivity.this.finish();
                Toast.makeText(SuccessActivity.this, "Code Submitted Successfully", Toast.LENGTH_LONG).show();


            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(SuccessActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            }

            else if(result.equalsIgnoreCase("no"))
            {
                Toast.makeText(SuccessActivity.this, "Your location are not matching.", Toast.LENGTH_LONG).show();

            }

            else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(SuccessActivity.this, "Your attendace is marked already.", Toast.LENGTH_LONG).show();

            }
        }

    }
}