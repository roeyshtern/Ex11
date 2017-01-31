package com.example.user.ex11.View;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.ex11.Model.Country;
import com.example.user.ex11.R;

/**
 * Created by User on 1/3/2017.
 */

public class DetailsFragment extends Fragment{
    CountryReporter listener;
    Context context;

    TextView tvDetails;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.context = getActivity();
        try
        {
            this.listener = (CountryReporter)getActivity();
            Country country = listener.getCountryData();
            if(null != country)
            {
                    changeTo(country);

            }

        }catch (ClassCastException e)
        {
            throw new ClassCastException("The class" + getActivity().getClass().getName() + " must impliments the inteface 'clickHandler' ");
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.tvDetails = (TextView) view.findViewById(R.id.details);

        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_frag, container,false);
    }
    public void changeTo(Country newCountry) {
        tvDetails.setText(newCountry.getDetails());

    }
    public interface CountryReporter
    {
        public Country getCountryData();
    }
}
