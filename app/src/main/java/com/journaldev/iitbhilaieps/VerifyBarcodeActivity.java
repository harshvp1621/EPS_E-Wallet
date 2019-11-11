package com.journaldev.iitbhilaieps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import android.content.Intent;

import com.journaldev.barcodevisionapi.R;

// This activity is invoked from the ScannedBarcodeActivity. The verification of the QR code will take place here.
public class VerifyBarcodeActivity extends AppCompatActivity {
    TextView rawQRData = findViewById(R.id.qrCodeData);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_barcode);
        initViews();
    }

    private void initViews(){
        if (getIntent().getStringExtra("qrData") != null) {
//            rawQRData.setText("Raw QR Data : " + getIntent().getStringExtra("qrData"));
            rawQRData.setText("Raw QR Data : ");
        }

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnScanQR:
                startActivity(new Intent(VerifyBarcodeActivity.this, ScannedBarcodeActivity.class));
                break;
        }

    }
}
