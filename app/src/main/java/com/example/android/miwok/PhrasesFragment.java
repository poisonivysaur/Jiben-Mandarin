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
 * {@link Fragment} that displays a list of phrases.
 */
public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("PHRASES", "字詞 (Zì cí)", R.raw.category_phrases));
        words.add(new Word("Where are you going?", "你要去哪裡？ (Nǐ yào qù nǎlǐ?)", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "你叫什麼名字？ (Nǐ jiào shénme míngzì?)", R.raw.phrase_what_is_you_name));
        words.add(new Word("My name is...", "我叫... (Wǒ jiào...)", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "你感覺怎麼樣？ (Nǐ gǎnjué zěnme yàng?)", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "我感覺不錯。 (Wǒ gǎnjué bùcuò.)", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "你來嗎？ (Nǐ lái ma?)", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "是的，我來了。 (Shì de, wǒ láile.)", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "我來了 (Wǒ láile)", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "我們走吧。 (Wǒmen zǒu ba.)", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "過來。 (Guòlái.)", R.raw.phrase_come_here));

        // ################################## ListView ##################################
        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases, R.layout.larger_list_item);

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
