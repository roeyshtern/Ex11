package com.example.user.ex11.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.user.ex11.R;

/**
 * Created by User on 12/16/2016.
 */

public class MyDialog extends DialogFragment {
    private int requestCode = 0;
    final public static int ADD = 1;
    final public static int PRECISION = 2;
    final public static int EXIT = 3;
    private ResultsListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.requestCode = getArguments().getInt("requestCode");
        Log.d("ex11", "MyDialog onCreateDialog - rc= " + requestCode);
        if(requestCode == ADD) {
            return buildPreciionDialog().create();
        }
        else
        {
            return buildExitDialog().create();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        try
        {
            if(getParentFragment()!=null)
            {
                this.listener = (ResultsListener)getParentFragment();
            }
            else
            {
                this.listener = (ResultsListener)activity;
            }
        }catch(ClassCastException e)
        {
            String str = getResources().getString(R.string.host);
            throw new ClassCastException(str);
        }
        super.onAttach(activity);
    }
    /*
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
*/
    @Override
    public void onDetach() {

        this.listener = null;
        super.onDetach();
    }
    public static MyDialog newInstance(int requestCode) {
        Log.d("ex11", "MyDialog new Instance");
        Bundle args = new Bundle();
        MyDialog fragment = new MyDialog();
        args.putInt("requestCode",requestCode);
        fragment.setArguments(args);
        return fragment;
    }
    private AlertDialog.Builder buildExitDialog()
    {
        Log.d("ex11", "MyDialog buildExitDialog");
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.closing)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.OnfinishDialog(requestCode, "ok");

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }
    private AlertDialog.Builder buildPreciionDialog(){
        ItemFragment ifrag = new ItemFragment();
        final String[] selected = new String[1];
        View view = getActivity().getLayoutInflater().inflate(R.layout.spinner_layout, null);
        Spinner sp = (Spinner)view.findViewById(R.id.spinner);
        sp.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                ifrag.getMissingCountry()));
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected[0]= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dismiss();
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add)
                .setView(view)
                .setPositiveButton(R.string.yes ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.OnfinishDialog(ADD, selected[0]);

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }

    public interface ResultsListener
    {
        public void OnfinishDialog(int requestCode, Object result);
    }
}