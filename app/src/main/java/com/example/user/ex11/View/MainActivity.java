package com.example.user.ex11.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.user.ex11.Model.Country;
import com.example.user.ex11.R;

public class MainActivity extends Activity implements ItemFragment.CountrySelectionListener, DetailsFragment.CountryReporter {
    private Country country = null;
    private int selectPos = -1;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectPos",this.selectPos);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null)
        {
            this.selectPos = savedInstanceState.getInt("selectPos");
        }
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            FragmentManager fm = getFragmentManager();
            if(savedInstanceState != null && fm.findFragmentByTag("AAA")!=null)
            {
                return;
            }
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragContainer, new ItemFragment(), "AAA").commit();
        }

    }


    @Override
    public void setInitCountry(Country country) {
        this.country = country;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            DetailsFragment detailsFragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
            if(detailsFragment.isVisible()) {
                detailsFragment.changeTo(country);
            }
        }
    }

    @Override
    public int getCurrentSelection() {
        return this.selectPos;
    }

    @Override
    public void onCountryChanged(int position, Country country) {
        this.selectPos = position;
        DetailsFragment detailsFragment;
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
        {
            detailsFragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
        }
        else
        {
            detailsFragment = new DetailsFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .add(R.id.fragContainer, detailsFragment, "BBB")
                    .addToBackStack("BBB")
                    .commit();
            fm.executePendingTransactions();
        }
        this.country = country;

        detailsFragment.changeTo(this.country);

    }

    @Override
    public Country getCountryData() {
        return this.country;
    }
}
