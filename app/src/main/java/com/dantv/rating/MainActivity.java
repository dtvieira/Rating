package com.dantv.rating;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dantv.rating.util.RateDialogFrag;
import com.dantv.rating.util.RateDialogManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RateDialogManager.showRateDialog(this, savedInstanceState);

    }

    public void clicar(View v){
        FragmentManager fm = RateDialogManager.getFragManager( this );
        RateDialogFrag dialog = new RateDialogFrag();
        dialog.setCancelable(false);
        dialog.show( fm, RateDialogFrag.KEY );
    }

}
