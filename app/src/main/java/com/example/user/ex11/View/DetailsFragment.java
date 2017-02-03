package com.example.user.ex11.View;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.MediaController;
import android.widget.TextView;

import com.example.user.ex11.Model.Country;
import com.example.user.ex11.R;

import java.io.IOException;

/**
 * Created by User on 1/3/2017.
 */

public class DetailsFragment extends Fragment implements MediaController.MediaPlayerControl{
    CountryReporter listener;
    Context context;
    private static MediaPlayer mediaPlayer;
    private MediaController mMediaController;
    private Handler mHandler = new Handler();

    TextView tvDetails;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.context = getActivity();
        try
        {
            this.listener = (CountryReporter)getActivity();
            Country country = listener.getCountryData();
            if(null != country)
            {
                    changeTo(country);

            }

        }catch (ClassCastException e)
        {
            throw new ClassCastException("The class" + getActivity().getClass().getName() + " must impliments the inteface 'clickHandler' ");
        }

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onPause() {
        if(mediaPlayer!=null)
            mediaPlayer.pause();
        super.onPause();
    }
/*
    @Override
    public void onStart() {
        if(mediaPlayer!=null)
            mediaPlayer.start();
        super.onStart();
    }
    */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.tvDetails = (TextView) view.findViewById(R.id.details);
        final TextView button = (TextView)view.findViewById(R.id.details);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMediaController.show(10000);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_frag, container,false);
    }
    public void changeTo(Country newCountry) {
        tvDetails.setText(newCountry.getDetails());
        if(mediaPlayer!=null)
        {
            if(this.mediaPlayer.isPlaying()==true)
                mediaPlayer.stop();
        }
        int songResource = getResources().getIdentifier(newCountry.getAnthem(), "raw", getActivity().getPackageName());
        mediaPlayer = new MediaPlayer();
        mMediaController = new MediaController(getActivity());
        mMediaController.setMediaPlayer(DetailsFragment.this);
        mMediaController.setAnchorView(tvDetails);
        mediaPlayer = MediaPlayer.create(getActivity(), songResource);


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mHandler.post(new Runnable() {
                    public void run() {
                        mMediaController.show(10000);
                        mediaPlayer.start();
                    }
                });
            }
        });
    }


    public void onClick(View v)
    {
        mMediaController.show(10000);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        int percentage = (mediaPlayer.getCurrentPosition() * 100) / mediaPlayer.getDuration();
        return percentage;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    public interface CountryReporter
    {
        public Country getCountryData();
    }
}
