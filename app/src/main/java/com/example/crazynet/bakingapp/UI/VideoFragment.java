package com.example.crazynet.bakingapp.UI;

import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CrazyNet on 03/03/2019.
 */

public class VideoFragment extends Fragment{

    private ArrayList<StepsItem> arrayList;
    private int position ;
    private static Long time = Long.valueOf(0);
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
            time = savedInstanceState.getLong("time");
            SharedPreferences.Editor editor = getContext().getSharedPreferences("shared", MODE_PRIVATE).edit();
            editor.putLong("time", time);
            editor.apply();
        }

        if(arrayList != null){
            TextView textView = view.findViewById(R.id.text);
            textView.setText(String.valueOf(arrayList.get(position).getDescription()));

            if(mExoPlayer != null){
                mExoPlayer.setPlayWhenReady(false);
                mExoPlayer.release();
                mExoPlayer.stop();
                mExoPlayer = null;
            }



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

    private void initializePlayer(Uri mediaUri , Long time) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);


            String userAgent = Util.getUserAgent(this.getContext(), "bakingapp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(time);
            mExoPlayer.setPlayWhenReady(true);
          /*  mExoPlayer.addListener(new ExoPlayer.EventListener() {

                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });

            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this.getContext(), "media");
            mediaSessionCompat.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL);
            mediaSessionCompat.setCallback(new MediaSessionCompat.Callback() {
                @Override
                public void onPlay() {
                    super.onPlay();
                    Toast.makeText(VideoFragment.this.getContext(), "Played", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPause() {
                    super.onPause();
                    Toast.makeText(VideoFragment.this.getContext(), "paused", Toast.LENGTH_LONG).show();
                }
            });

            MediaSessionConnector mediaSessionConnector =
                    new MediaSessionConnector(mediaSessionCompat);
            mediaSessionConnector.setPlayer(mExoPlayer, null);*/
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        time = getContext().getSharedPreferences("shared", MODE_PRIVATE).getLong("time",Long.valueOf(0));
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        if (mExoPlayer != null) {
            time = mExoPlayer.getCurrentPosition();
            mExoPlayer.setPlayWhenReady(false);
            mExoPlayer.release();
            mExoPlayer = null;
            //mPlayerView = null;
        }
        super.onPause();
    }


    @Override
    public void onStart() {
        initializePlayer(Uri.parse(arrayList.get(position).getVideoURL()),time);
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("mySteps",arrayList);
        outState.putInt("pos",position);
        outState.putLong("time",time);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        SharedPreferences.Editor sharedPreferences = getContext().getSharedPreferences("shared", MODE_PRIVATE).edit();
        sharedPreferences.putLong("time",Long.valueOf(0));
        sharedPreferences.apply();
        super.onStop();
    }
}
