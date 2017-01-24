package com.example.user.ex11.Model;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.user.ex11.View.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by User on 1/24/2017.
 */

public class DataLoader {
    public static ArrayList<Country> getCountries()
    {
        ArrayList<Country> data = null;
        Context context = MyApplication.getAppContext();
        try {
            InputStream inputStream = openAssetDataStream(context);
            data = XMLParser.parseFromStream(inputStream);
            inputStream.close();
        }catch(IOException e){e.printStackTrace();}
        return data;
    }
    private static InputStream openAssetDataStream(Context context)
    {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try
        {
            inputStream = assetManager.open("countries.xml");
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return inputStream;
    }
}
