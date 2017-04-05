package com.matheus.jogodedamas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int teste = getStringIdentifier(this,"a1");
        ImageView img = (ImageView) findViewById(teste);

    }

    public static int getStringIdentifier(Context pContext, String pString){
        return pContext.getResources().getIdentifier(pString, "id", pContext.getPackageName());
    }


}
