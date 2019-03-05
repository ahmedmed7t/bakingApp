package com.example.crazynet.bakingapp.UI;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crazynet.bakingapp.R;
import com.example.crazynet.bakingapp.model.StepsItem;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by CrazyNet on 03/03/2019.
 */

public class VideoFragment extends Fragment{

    private ArrayList<StepsItem> arrayList;
    private int position ;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;


    public VideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.video_fragment,container,false);

        mPlayerView = view.findViewById(R.id.exoplayer);

        if(savedInstanceState != null){
            arrayList = savedInstanceState.getParcelableArrayList("mySteps");
            position = savedInstanceState.getInt("pos");
        }

        if(arrayList != null){
            TextView textView = view.findViewById(R.id.text);
            textView.setText(String.valueOf(arrayList.get(position).getDescription()));
            if(mExoPlayer != null){
                mExoPlayer.release();
                mExoPlayer.stop();
                mExoPlayer = null;
            }
            initializePlayer(Uri.parse(arrayList.get(position).getVideoURL()));

            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                ConstraintLayout.LayoutParams params=new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT);
                    textView.setVisibility(View.GONE);
                    mPlayerView.setLayoutParams(params);

            }
        }

        return view;
    }

    public void showData(ArrayList<StepsItem> stepsItems , int mposition){

       arrayList = stepsItems ;
       position = mposition;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
           // mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this.getContext(), "bakingapp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onPause() {

        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer.stop();
            mExoPlayer = null;
            //mPlayerView = null;
        }
        super.onPause();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("mySteps",arrayList);
        outState.putInt("pos",position);
        super.onSaveInstanceState(outState);
    }


}
