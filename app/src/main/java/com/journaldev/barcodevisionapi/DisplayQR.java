package com.journaldev.barcodevisionapi;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import android.content.Intent;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class DisplayQR extends AppCompatActivity{

    ImageView qrImg;
    String raw64;
//    byte[] raw64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qr);
        initViews();
    }

    private void initViews(){
        qrImg = findViewById(R.id.qrImg);
        if (getIntent().getStringExtra("base64string") != null) {
            raw64 = getIntent().getStringExtra("base64string");
        }
        if(raw64 != null) {
//            byte [] decodedString = Base64.decode(raw64, Base64.DEFAULT);

            byte[] decodedString = Base64.decode(raw64,Base64.DEFAULT);

            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            if(decodedByte != null)
                qrImg.setImageBitmap(resizeBitmapImage(decodedByte, 256, 256));
            else
//                Toast.makeText(getApplicationContext(), "NULL Decoded", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "RAW "+ raw64, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "NULL String Returned", Toast.LENGTH_SHORT).show();
        }
    }
    private static Bitmap resizeBitmapImage(Bitmap bm, int newWidth, int newHeight){
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaledWidth = ((float)newWidth)/width;
        float scaledHeight = ((float)newHeight)/height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaledWidth, scaledHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm,0,0,width,height,matrix,false);
        bm.recycle();;
        return resizedBitmap;
    }
}
