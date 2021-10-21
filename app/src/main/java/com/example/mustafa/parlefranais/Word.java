package com.example.mustafa.parlefranais;

public class Word {


    private String menglish_translation;
    private String mfrench_translation;
    private int mImageResourceId;
    private int mAudioResourceId;



    public Word(String english_translation,String french_translation,int audioResourceId) {


        menglish_translation = english_translation;
        mfrench_translation = french_translation;
        mAudioResourceId = audioResourceId;

    }




    public Word(String english_translation,String french_translation,int imageResourceId,int audioResourceId) {


        menglish_translation = english_translation;
        mfrench_translation = french_translation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;

    }


    public String getenglish_translation(){
        return menglish_translation;
    }


    public String getfrench_translation() {
        return mfrench_translation;
    }


    public int getImageResourceId() {
        return mImageResourceId;
    }


    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
