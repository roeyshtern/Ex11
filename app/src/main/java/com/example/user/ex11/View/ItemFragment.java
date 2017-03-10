package com.example.user.ex11.View;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.ex11.Controller.CountryAdapter;
import com.example.user.ex11.Model.Country;
import com.example.user.ex11.Model.DataLoader;
import com.example.user.ex11.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 1/3/2017.
 */

public class ItemFragment extends ListFragment implements MyDialog.ResultsListener ,CountryAdapter.CountryAdapterListener{
    public CountrySelectionListener listener;
    Context context;
    static CountryAdapter adapter;
    static ArrayList<String> countries;
    Menu fragMenu;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if(savedInstanceState!=null) {
            this.countries = savedInstanceState.getStringArrayList("shwon");
        }
        else {
            if(countries==null)
                this.countries = new ArrayList<>();
        }
        this.context = getActivity();
        try
        {
            this.listener = (CountrySelectionListener) getActivity();
            this.adapter = new CountryAdapter(context, this, countries);

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
            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter.onItemLongClick(position);
                    setListAdapter(adapter);
                    listener.setInitCountry(null);
                    return true;
                }
            });

        }catch(ClassCastException e)
        {
            throw new ClassCastException("The class" + getActivity().getClass().getName() + " must impliments the inteface 'clickHandler' ");
        }
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(menu.findItem(R.id.action_add)==null)
        {
            inflater.inflate(R.menu.main,menu);
        }
        this.fragMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add: {
                MyDialog.newInstance(MyDialog.ADD).show(getChildFragmentManager(), "spinner");
                return true;
            }
            default:
                return false;
        }
    }

    public ArrayList<String> getMissingCountry()
    {
        if(countries==null)
        {
            countries = new ArrayList<>();
        }
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
        switch(requestCode)
        {
            case MyDialog.ADD:
                adapter.addNewCountry(result.toString());

        }
        Toast.makeText(this.getActivity(), "this string: " + result.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void UpdateShownCountriesList(boolean toAdd, String name) {
        if(toAdd)
        {
            this.countries.add(name);
        }
        else
        {
            this.countries.remove(name);
        }
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

    @Override
    public void onDestroy() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sp.edit();
        boolean toSaveList = sp.getBoolean("toSaveList", false);
        if(toSaveList)
        {
            Set<String> set = new HashSet<>();
            set.addAll(countries);
            editor.putStringSet("shown", set);
        }
        else
        {
            editor.remove("shown");
        }
        editor.commit();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("shwon", this.countries);
    }

}
