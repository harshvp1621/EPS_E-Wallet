package com.journaldev.barcodevisionapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class PaymentActivity extends AppCompatActivity {

    Button btnPay;
    TextView textViewCurrBalance;
    EditText eTxtRecipientID, eTxtAmount;
    CheckBox generateQR;

    String currBalance = "0";
    String recipientID;
    String amount;
    String base64response;
    String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        SharedPreferences session = getSharedPreferences("AuthTokens", Context.MODE_PRIVATE);
        authToken = session.getString("11640420", "-1");

        initViews();
    }

    private void initViews() {
        StrictMode.ThreadPolicy policy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy2);

        // Payment ACK and NACK
        // Go to a payments successful page

        textViewCurrBalance = findViewById(R.id.textBalance);
        eTxtRecipientID = findViewById(R.id.eTxtRecipient);
        eTxtAmount = findViewById(R.id.extTxtAmount);
        generateQR = findViewById(R.id.checkBoxWantQR);

        try {
            currBalance = GetJsonResponse.getBalance("http://10.2.77.214:5000/api/get_bal");
            // Extract the balance here
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to fetch balance", Toast.LENGTH_SHORT).show();
            currBalance = "-1";

            e.printStackTrace();
        }

        btnPay = findViewById(R.id.btnPay);

        textViewCurrBalance.setText("Balance: " + currBalance);

            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    recipientID = eTxtRecipientID.getText().toString();
                    amount = eTxtAmount.getText().toString();

                    if (generateQR.isChecked()) {
                        try {
                            base64response = GetBase64String.getBase64StringFromURL("http://10.2.77.214:5000/api/get_qr", recipientID, amount, authToken);
                            if (base64response == null)
                                Toast.makeText(getApplicationContext(), "NULL Response", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Unable to Reach Server", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(PaymentActivity.this, DisplayQR.class).putExtra("base64string", base64response));
                    } else {
                        Toast.makeText(getApplicationContext(), "Payment Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

}
