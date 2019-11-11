package com.journaldev.iitbhilaieps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import android.os.StrictMode;

import com.journaldev.barcodevisionapi.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    UserSessionManager session;
    String authToken;

    Button btnScanBarcode, btnPaymentsPage, btnLogout, btnViewQR, btnCheckBal;
    JSONObject response = new JSONObject();
    String base64response;
//    byte[] base64response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session =  new UserSessionManager(getApplicationContext());

//        Toast.makeText(getApplicationContext(),
//                "User Login Status: " + session.isUserLoggedIn(),
//                Toast.LENGTH_LONG).show();

        if(session.checkLogin())
            finish();

        HashMap<String, String> user = session.getUserDetails();
        authToken = user.get("authToken");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
//                    response = GetJsonResponse.getJSONObjectFromURL("https://api.myjson.com/bins/r9iic");
            response = GetJsonResponse.getJSONObjectFromURL("http://192.168.43.167:5000/api/get_puk");
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
            Toast.makeText(getApplicationContext(), "Unable to Fetch Public Key", Toast.LENGTH_SHORT).show();
        }

        initViews();

    }

    private void initViews() {
//        btnGetPubKey = findViewById(R.id.btnGetPubKey);
        btnScanBarcode = findViewById(R.id.btnScanQR);
        btnPaymentsPage = findViewById(R.id.btnPaymentsPage);
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
        btnPaymentsPage.setOnClickListener(this);
//        btnGetPubKey.setOnClickListener(this);
        btnScanBarcode.setOnClickListener(this);

        btnViewQR = findViewById(R.id.btnViewQR);
        btnViewQR.setOnClickListener(this);

        btnCheckBal = findViewById(R.id.btnCheckBal);
        btnCheckBal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnCheckBal:
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                String currBalance;
                try {
                    currBalance = GetJsonResponse.getBalance("http://192.168.43.167:5000/api/check_balance", authToken);
                    Toast.makeText(getApplicationContext(), "Balance: "+currBalance, Toast.LENGTH_SHORT).show();
                    // Extract the balance here
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to fetch balance", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                break;
            case R.id.btnLogout:
                session.logoutUser();
                finish();
                break;
            case R.id.btnScanQR:
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
            case R.id.btnViewQR:
                startActivity(new Intent(MainActivity.this, ViewPastQRActivity.class));
                break;
        }

    }
}
