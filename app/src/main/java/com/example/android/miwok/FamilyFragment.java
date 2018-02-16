package com.example.android.miwok;


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
 * {@link Fragment} that displays a list of family vocabulary words.
 */
public class FamilyFragment extends Fragment {

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("FAMILY MEMBERS", "家人 (Jiārén)", R.raw.category_family));
        words.add(new Word("father", "父親 (Fùqīn)", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("father （informal）", "爸爸 (Bàba)", R.drawable.family_father, R.raw.family_dad));
        words.add(new Word("mother", "母親 (Mǔqīn)", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("mother (informal)", "媽媽 (Māmā)", R.drawable.family_mother, R.raw.family_mom));
        words.add(new Word("son", "兒子 (Érzi)", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "女兒 (Nǚ'ér)", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "哥哥 (Dàgē)", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("older sister", "姐姐 (Dàjiě)", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger brother", "弟弟 (Xiǎodì)", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("younger sister", "妹妹 (Xiǎo mèi)", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandpa (father side)", "爺爺 (Yéyé)", R.drawable.family_grandfather, R.raw.family_grandpa));
        words.add(new Word("grandma (mother side)", "奶奶 (Nǎinai)", R.drawable.family_grandmother, R.raw.family_grandma));

        // ################################## ListView ##################################
        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(NumbersActivity.this, "List item clicked", Toast.LENGTH_SHORT).show();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(i);

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word
                mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());

                // Start the audio file
                mMediaPlayer.start();

                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
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

}
