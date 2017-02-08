package com.example.user.ex11.View;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.ex11.Controller.CountryAdapter;
import com.example.user.ex11.Model.Country;
import com.example.user.ex11.Model.DataLoader;
import com.example.user.ex11.R;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by User on 1/3/2017.
 */

public class ItemFragment extends ListFragment implements MyDialog.ResultsListener{
    CountrySelectionListener listener;
    Context context;
    CountryAdapter adapter;
    ArrayList<String> countries = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.countries = new ArrayList<>();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MyDialog.newInstance(MyDialog.EXIT_DIALOG).show(getChildFragmentManager(), "spinner");
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> getMissingCountry()
    {
        return adapter.getMissingCountries(countries);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.items_frag, container,false);
    }

    @Override
    public void OnfinishDialog(int requestCode, Object result) {
        Toast.makeText(this.getActivity(), "this string: " + result.toString(),Toast.LENGTH_SHORT).show();
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
