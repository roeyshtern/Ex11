package com.example.user.ex10;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by User on 1/3/2017.
 */

public class FragA extends Fragment implements View.OnClickListener{
    clickHandler listener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try
        {
            this.listener = (clickHandler) getActivity();

        }catch(ClassCastException e)
        {
            throw new ClassCastException("The class" + getActivity().getClass().getName() + " must impliments the inteface 'clickHandler' ");
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.pushMe).setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frage_a, container,false);
    }

    @Override
    public void onClick(View v) {
        this.listener.onClickEvent();
    }

    public interface clickHandler
    {
        public void onClickEvent();
    }
}
