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
public class PhrasesFragment extends Fragment {





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
        }
    }





    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };







    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getActionBar().setDisplayHomeAsUpEnabled(true);


        View rootView = inflater.inflate(R.layout.word_list, container, false);




        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);








        final ArrayList<Word> words =new ArrayList<Word>();

        words.add(new Word("where are you going","où allez-vous",R.raw.where_are_you_going));
        words.add(new Word("what's your name","comment tu t'appel",R.raw.whats_your_name));
        words.add(new Word("my name is ...","je m'appel ....",R.raw.my_name_is));
        words.add(new Word("how are you","comment tu va",R.raw.how_are_you));
        words.add(new Word("i am feeling good","je va bien",R.raw.i_am_feeling_well));
        words.add(new Word("are you coming","est-ce que tu viens",R.raw.are_you_coming));
        words.add(new Word("yes i am coming","Oui j'arrive",R.raw.yes_i_am_coming));
        words.add(new Word("lets go","Allons-y",R.raw.lets_go));
        words.add(new Word("come here","Venez ici",R.raw.come_here));
        words.add(new Word("you are beautiful","vous êtes belle",R.raw.zero));



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

