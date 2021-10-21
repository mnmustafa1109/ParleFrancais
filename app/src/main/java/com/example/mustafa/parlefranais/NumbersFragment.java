package com.example.mustafa.parlefranais;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {






    private MediaPlayer mMediaPlayer;



    private AudioManager mAudioManager ;





    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=
            new AudioManager.OnAudioFocusChangeListener() {

                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }
                    else if (focusChange== AudioManager.AUDIOFOCUS_GAIN){
                        mMediaPlayer.start();
                    }else if (focusChange==AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaPlayer();
                    }

                }
            };














    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);


        }
    }





    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };








    public NumbersFragment() {
        // Required empty public constructor
    }








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.word_list, container, false);


        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);






        final ArrayList<Word> words =new ArrayList<Word>();




        words.add(new Word("zero","zero",R.raw.zero));
        words.add(new Word("one","un",R.drawable.number_one,R.raw.one));
        words.add(new Word("two","deux",R.drawable.number_two,R.raw.two));
        words.add(new Word("three","trois",R.drawable.number_three,R.raw.three));
        words.add(new Word("four","quatre",R.drawable.number_four,R.raw.four));
        words.add(new Word("five","cinq",R.drawable.number_five,R.raw.five));
        words.add(new Word("six","six",R.drawable.number_six,R.raw.six));
        words.add(new Word("seven","sept",R.drawable.number_seven,R.raw.seven));
        words.add(new Word("eight","huit",R.drawable.number_eight,R.raw.eight));
        words.add(new Word("nine","neuf",R.drawable.number_nine,R.raw.nine));
        words.add(new Word("ten","dix",R.drawable.number_ten,R.raw.ten));






        WordAdapter Adapter = new WordAdapter(getActivity(),words);




        ListView listView = (ListView) rootView.findViewById(R.id.list);




        listView.setAdapter(Adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                Word word = words.get(position);


                releaseMediaPlayer();



                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);


                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){




                    mMediaPlayer=MediaPlayer.create(getActivity() , word.getAudioResourceId());


                    mMediaPlayer.start();


                    mMediaPlayer.setOnCompletionListener(mCompletionListener);


                }

            }
        });


        return rootView;





    }


    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }


}
