package com.example.isangeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PlaySong extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    TextView textView;
ImageView play,previous,next;
ArrayList<File> songs;
MediaPlayer mediaPlayer;
String textContent;
Button reset;
int position;
SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        textView = findViewById(R.id.textView);
        play = findViewById(R.id.play);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        seekBar = findViewById(R.id.seekBar);
        reset = findViewById(R.id.reset);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        songs = (ArrayList)bundle.getParcelableArrayList("songsList");
        textContent = intent.getStringExtra("currentSong");
        textView.setText(textContent);
        textView.setSelected(true);

        position = intent.getIntExtra("position",0);
        Uri uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this,uri);


        seekBar.setMax(mediaPlayer.getDuration());


        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override

            public void run() {

                try {

                    seekBar.setProgress(mediaPlayer.getCurrentPosition());

                } catch (Exception e) {

                }

            }

        }, 0, 1000);






        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                if(fromUser)
                {
                    mediaPlayer.seekTo(progress);

                }

//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        playNextSong();
//
//                    }
//                });




            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playNextSong();

                    }
                });


            }
        });



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.play);


                }
                else{
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.pause);

                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=0)
                {
                    position = position-1;
                }
                else{
                    position = songs.size()-1;

                }

                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);


                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);

                textContent = songs.get(position).getName().toString();
                textView.setText(textContent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=songs.size()-1)
                {
                    position = position+1;
                }
                else{
                    position = 0;

                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);


                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);

                textContent = songs.get(position).getName().toString();
                textView.setText(textContent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });







    }

    public void playNextSong(){
        if(position!=songs.size()-1)
        {
            position = position+1;
        }
        else{
            position = 0;

        }
        Uri uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);


        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        play.setImageResource(R.drawable.pause);

        textContent = songs.get(position).getName().toString();
        textView.setText(textContent);
    }

}