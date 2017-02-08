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

/**
 * Created by User on 1/31/2017.
 */

public class CountryAdapter extends ArrayAdapter<Country> {
    ArrayList<Country> countries;
    Context context;
    public CountryAdapter(Context context) {
        super(context, R.layout.row_layout);
        this.context = context;
        countries = new ArrayList<>(DataLoader.getCountries());
        this.addAll(countries);
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

}
