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

import java.net.InetAddress;
import java.net.MalformedURLException;

import static org.json.JSONObject.NULL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnGetPubKey, btnScanBarcode, btnQRCode;
    JSONObject response = new JSONObject();
    String base64response;
//    byte[] base64response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnGetPubKey = findViewById(R.id.btnGetPubKey);
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        btnQRCode = findViewById(R.id.btnGetQR);
        btnQRCode.setOnClickListener(this);
        btnGetPubKey.setOnClickListener(this);
        btnScanBarcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnGetPubKey:
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try {
//                    response = GetJsonResponse.getJSONObjectFromURL("https://api.myjson.com/bins/r9iic");
                    response = GetJsonResponse.getJSONObjectFromURL("http://10.2.77.214:5000/api/get_qr");
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
//                startActivity(new Intent(MainActivity.this, PictureBarcodeActivity.class));
                break;
            case R.id.btnScanBarcode:
                try{
                    startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class).putExtra("publicKey", response.getString("Key")));
                }
                catch(JSONException e){
                    Toast.makeText(getApplicationContext(), "Get Public Key First", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnGetQR:
                StrictMode.ThreadPolicy policy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy2);
                try{
                    base64response = GetBase64String.getBase64StringFromURL("http://10.2.77.214:5000/api/get_qr");
                    if(base64response == null)
                        Toast.makeText(getApplicationContext(), "NULL Response", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Unable to Reach Server", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(MainActivity.this, DisplayQR.class).putExtra("base64string", base64response));
                break;
        }

    }
}
