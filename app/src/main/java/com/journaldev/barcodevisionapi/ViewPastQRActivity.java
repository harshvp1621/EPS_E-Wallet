package com.journaldev.barcodevisionapi;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ViewPastQRActivity extends AppCompatActivity {

    ImageView loadQR;
    Bitmap b;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pastqr);
        loadQR = findViewById(R.id.loadQR);
        initViews();
    }
    private void initViews(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,"QR1");
        try{
            b = BitmapFactory.decodeStream(new FileInputStream(mypath));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        loadQR.setImageBitmap(b);

    }
}
