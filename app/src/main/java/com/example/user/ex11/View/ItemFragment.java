package com.example.user.ex11.View;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.ex11.Controller.CountryAdapter;
import com.example.user.ex11.Model.Country;
import com.example.user.ex11.Model.DataLoader;
import com.example.user.ex11.R;

import java.util.Comparator;

/**
 * Created by User on 1/3/2017.
 */

public class ItemFragment extends ListFragment{
    CountrySelectionListener listener;
    Context context;
    CountryAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.context = getActivity();
        try
        {
            this.listener = (CountrySelectionListener) getActivity();
            this.adapter = new CountryAdapter(context);

            this.adapter.sort(new Comparator<Country>() {
                @Override
                public int compare(Country o1, Country o2) {
                    return o1.compare(o2);
                }
            });
            setListAdapter(this.adapter);

            final int pos = listener.getCurrentSelection();


            getListView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(pos!=-1)
                    {
                        getListView().requestFocusFromTouch();
                        getListView().setSelection(pos);
                        getListView().setSelector(android.R.color.holo_blue_dark);
                    }
                }
            }, 100);

            if(pos!=-1)
            {
                listener.setInitCountry(adapter.getItem(pos));
            }

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
        this.listener.onCountryChanged(position, this.adapter.getItem(position));
    }
}
