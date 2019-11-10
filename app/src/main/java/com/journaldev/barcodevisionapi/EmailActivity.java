package com.journaldev.barcodevisionapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.*;
//import java.util.*;

public class EmailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inSubject, inBody;
    TextView txtEmailAddress;
    Button btnSendEmail;

    String strPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAZT/0N5Fyk9a0RYd2RRd+9c70dE2CbGIhs3SO/SW7kuLYy5NIhm0CSjxGonWSMQTQYV3ZSWZqRr/mueR/1fMGcI4PHdB4lasWxamzJmy0XA/Ow/ctf/U+i5mvPdQNE2Cnn144nf+FBfPNiLEV+tmLbSiq2x+DHOGsiDSWZNyM5wIDAQAB";

    String [] values;
    String raw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        initViews();
    }

    private void initViews() {

        txtEmailAddress = findViewById(R.id.txtEmailAddress);


        if (getIntent().getStringExtra("email_address") != null) {
            raw = getIntent().getStringExtra("email_address");
            values = raw.split(",");
            txtEmailAddress.setText("SampleString : " + values[1]);
        }
        PublicKey key = RSA.getKey(strPublicKey);
        byte[] message = values[1].getBytes();
        boolean verdict = false;
        try {
            verdict = RSA.verify(message, values[0], values[2]);
            if(verdict)
                Toast.makeText(getApplicationContext(), "Correct Signature", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Incorrect Signature", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "AlgoException", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btnTakePicture:
//                startActivity(new Intent(EmailActivity.this, PictureBarcodeActivity.class));
//                break;
            case R.id.btnScanBarcode:
                startActivity(new Intent(EmailActivity.this, ScannedBarcodeActivity.class));
                break;
        }

    }
}
