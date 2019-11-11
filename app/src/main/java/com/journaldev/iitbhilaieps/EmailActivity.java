package com.journaldev.iitbhilaieps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.journaldev.barcodevisionapi.R;

import java.security.*;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.*;

// This class verifies the QR code generated
public class EmailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inSubject, inBody;
    TextView txtEmailAddress;
    Button btnSendEmail;

    String strPublicKey = "MFswDQYJKoZIhvcNAQEBBQADSgAwRwJAcUbkc76P/Sg56UsY1K+gsE24bhMVcG5lcn1sqCUDDuLBESGdN8sSoHLrZa6Vy2RYGmgeOXSdPgzY2rWLAO6QNQIDAQAB";

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
            SimpleDateFormat timestamp = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
            String ts = timestamp.format(new Date());
            txtEmailAddress.setText("Transaction ID : " + values[2] + "\nAmount: " + values[4] + "\nTimestamp: " + ts + "\nIndex: " + values[5]);
        }
        PublicKey key = RSA.getKey(strPublicKey);
//        byte[] message = values[1].getBytes();
        String mess = "ServerGeneratedTransac.";
        byte[] message = mess.getBytes();
        boolean verdict = false;
        try {
            verdict = RSA.verify(message, values[0],values[1]);
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
            case R.id.btnScanQR:
                startActivity(new Intent(EmailActivity.this, ScannedBarcodeActivity.class));
                break;
        }

    }
}
