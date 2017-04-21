package com.example.user.ex11.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.ex11.Model.Country;
import com.example.user.ex11.Model.DataLoader;
import com.example.user.ex11.R;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by User on 1/31/2017.
 */

public class CountryAdapter extends ArrayAdapter<Country> {
    ArrayList<Country> countries;
    Context context;
    private Country country;
    public CountryAdapterListener listener;
    public CountryAdapter(Context context, CountryAdapterListener listener, ArrayList<String> shownCountries) {
        super(context, R.layout.row_layout);
        this.listener = listener;
        this.context = context;
        countries = new ArrayList<>(DataLoader.getCountries());
        this.feedShownCountries(shownCountries);
    }
    private void feedShownCountries(ArrayList<String> shownCountriesNames)
    {
        for (Country country:countries) {
            if(shownCountriesNames.contains(country.getName()))
            {
                add(country);
            }
        }
    }
    public void onItemLongClick(int position)
    {
        country=getItem(position);
        remove(country);
        listener.UpdateShownCountriesList(false,country.getName());
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout,parent,false);
        }
        TextView tvName = (TextView)convertView.findViewById(R.id.name);
        TextView tvShort = (TextView)convertView.findViewById(R.id.SD);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.flag);
        Country country = getItem(position);
        tvName.setText(country.getName());
        tvShort.setText(country.getShortDetails());
        int imageResource = context.getResources().getIdentifier(country.getFlag(), "drawable",parent.getContext().getPackageName());
        imageView.setImageResource(imageResource);
        return convertView;
    }
    public ArrayList<String> getMissingCountries(ArrayList<String> countries)
    {
        ArrayList<String> missing = new ArrayList<>();
        for (Country country:this.countries)
        {
            if(!countries.contains(country.getName()))
            {
                missing.add(country.getName());
            }
        }
        return missing;
    }
    public void sort()
    {
        this.sort(new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
    public void addNewCountry(String string)
    {
        for (Country country: this.countries) {
            if(country.getName().equals(string))
            {
                add(country);
                listener.UpdateShownCountriesList(true,string);
                break;
            }
        }
        sort();
    }
    public interface CountryAdapterListener
    {
        public void UpdateShownCountriesList(boolean toAdd,String name);
    }
}
