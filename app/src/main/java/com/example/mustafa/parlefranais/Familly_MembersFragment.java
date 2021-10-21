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
public class Familly_MembersFragment extends Fragment {







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










    public Familly_MembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View rootView = inflater.inflate(R.layout.word_list, container, false);


        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);









        final ArrayList<Word> words =new ArrayList<Word>();


        words.add(new Word("father","père",R.drawable.family_father,R.raw.father));
        words.add(new Word("mother","mère",R.drawable.family_mother,R.raw.mother));
        words.add(new Word("son","fils",R.drawable.family_son,R.raw.son));
        words.add(new Word("daughter","fille",R.drawable.family_daughter,R.raw.daughter));
        words.add(new Word("older brother","grande fils",R.drawable.family_older_brother,R.raw.big_son));
        words.add(new Word("younger brother","petite fils",R.drawable.family_younger_brother,R.raw.small_son));
        words.add(new Word("older sister","grande fille",R.drawable.family_older_sister,R.raw.big_daughter));
        words.add(new Word("younger sister","petite fille",R.drawable.family_younger_sister,R.raw.small_daughter));
        words.add(new Word("grandmother","grande-mère",R.drawable.family_grandmother,R.raw.grand_mother));
        words.add(new Word("grandfather","grande-père",R.drawable.family_grandfather,R.raw.grand_father));





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
