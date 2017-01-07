package com.example.user.ex10;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity implements FragA.clickHandler, FragB.dataReporter {
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter",this.counter);
    }

    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            FragmentManager fm = getFragmentManager();
            if(savedInstanceState != null && fm.findFragmentByTag("AAA")!=null)
            {
                return;
            }
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragContainer, new FragA(), "AAA").commit();
        }
        if(savedInstanceState!=null)
        {
            this.counter = savedInstanceState.getInt("counter");
        }
    }

    @Override
    public void onClickEvent() {
        this.counter++;
        FragB fragb;
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
        {
            fragb = (FragB) getFragmentManager().findFragmentById(R.id.fragB);
        }
        else
        {
            fragb = new FragB();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .add(R.id.fragContainer, fragb, "BBB")
                    .addToBackStack("BBB")
                    .commit();
            fm.executePendingTransactions();
        }
        fragb.onCounterChange(this.counter);
    }

    @Override
    public int getCounter() {
        return this.counter;
    }
}
