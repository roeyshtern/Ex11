package com.example.user.ex11.View;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.user.ex11.Model.Country;
import com.example.user.ex11.R;

/**
 * Created by User on 1/3/2017.
 */

public class ItemFragment extends ListFragment{
    CountrySelectionListener listener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try
        {
            this.listener = (CountrySelectionListener) getActivity();

        }catch(ClassCastException e)
        {
            throw new ClassCastException("The class" + getActivity().getClass().getName() + " must impliments the inteface 'clickHandler' ");
        }
        super.onActivityCreated(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.items_frag, container,false);
    }


    public interface CountrySelectionListener
    {
        public void setInitCountry(Country country);
        public int getCurrentSelection();
        public void onCountryChanged(int position, Country country);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        getListView().setSelector(android.R.color.holo_blue_dark);
    }
}
