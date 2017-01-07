package com.example.user.ex10;

import android.app.Fragment;
import android.content.pm.FeatureGroupInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by User on 1/3/2017.
 */

public class FragB extends Fragment{
    dataReporter listener;

    TextView tvCounter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try
        {
            this.listener = (dataReporter)getActivity();
            this.tvCounter.setText(Integer.toString(listener.getCounter()));
        }catch (ClassCastException e)
        {
            throw new ClassCastException("The class" + getActivity().getClass().getName() + " must impliments the inteface 'clickHandler' ");
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.tvCounter = (TextView) view.findViewById(R.id.counter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frage_b, container,false);
    }
    public void onCounterChange(int newCounter)
    {
        tvCounter.setText(Integer.toString(newCounter));

    }
    public interface dataReporter
    {
        public int getCounter();
    }
}
