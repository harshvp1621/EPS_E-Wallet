package com.journaldev.barcodevisionapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import android.os.StrictMode;

import java.util.HashMap;
import android.text.Html;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.MalformedURLException;

import static org.json.JSONObject.NULL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    UserSessionManager session;

    Button btnScanBarcode, btnPaymentsPage, btnLogout;
    JSONObject response = new JSONObject();
    String base64response;
//    byte[] base64response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session =  new UserSessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        if(session.checkLogin())
            finish();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
//                    response = GetJsonResponse.getJSONObjectFromURL("https://api.myjson.com/bins/r9iic");
            response = GetJsonResponse.getJSONObjectFromURL("http://10.2.77.214:5000/api/get_puk");
            if(!response.isNull("Key")){
//                        try {
//                        String res = response.getString("html_url");
                Toast.makeText(getApplicationContext(),"Received Public Key" + response.getString("Key"),Toast.LENGTH_LONG).show();
//                        }
//                        catch (JSONException e){
//                            Toast.makeText(getApplicationContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
//                        }
            }
            else{
                Toast.makeText(getApplicationContext(), "NULL Val Returned", Toast.LENGTH_SHORT).show();
            }

        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), "Json Exception", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e){
            Toast.makeText(getApplicationContext(), "Unable to Reach Server", Toast.LENGTH_SHORT).show();
        }

        initViews();

    }

    private void initViews() {
//        btnGetPubKey = findViewById(R.id.btnGetPubKey);
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        btnPaymentsPage = findViewById(R.id.btnPaymentsPage);
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
        btnPaymentsPage.setOnClickListener(this);
//        btnGetPubKey.setOnClickListener(this);
        btnScanBarcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogout:
                session.logoutUser();
                finish();
                break;
            case R.id.btnScanBarcode:
                try{
                    startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class).putExtra("publicKey", response.getString("Key")));
                }
                catch(JSONException e){
                    Toast.makeText(getApplicationContext(), "Get Public Key First", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnPaymentsPage:
//                StrictMode.ThreadPolicy policy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy2);
//                try{
//                    base64response = GetBase64String.getBase64StringFromURL("http://10.2.77.214:5000/api/get_qr");
//                    if(base64response == null)
//                        Toast.makeText(getApplicationContext(), "NULL Response", Toast.LENGTH_SHORT).show();
//                }
//                catch (IOException e){
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Unable to Reach Server", Toast.LENGTH_SHORT).show();
//                }
                startActivity(new Intent(MainActivity.this, PaymentActivity.class).putExtra("base64string", base64response));
                break;
        }

    }
}
