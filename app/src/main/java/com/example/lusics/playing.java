package com.example.lusics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.io.File;
import java.lang.Object;
import java.lang.Class.*;

public class playing extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    TextView textView;
    ImageView play, previous, next;
    ArrayList list;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    Thread thread;
    String name;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        textView = findViewById(R.id.textView);
        textView.setSelected(true);
        play = findViewById(R.id.play);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        seekBar= findViewById(R.id.seekBar);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        list = bundle.getParcelableArrayList("songlist");
        name = intent.getStringExtra("currentsong");
        textView.setText(name);
        position = intent.getIntExtra("position", 0);
        Uri uri = Uri.parse(list.get(position).toString());
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.start();

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

       thread = new Thread() {
            @Override
            public void run() {
                int current = 0;
                try {
                    while (current <=mediaPlayer.getDuration()) {
                        current = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(current);
                        sleep(80);
                    }
                    while (current >= mediaPlayer.getDuration()) {
                        current = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(current);
                        sleep(80);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        thread.start();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
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
                if (position!=0){
                    position--;
                }
                else {
                    position= list.size()-1;
                }
                name= list.get((position)).toString();
                textView.setText(name);
                Uri uri = Uri.parse(list.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
                thread = new Thread() {
                    @Override
                    public void run() {
                        int current = 0;
                        try {
                            while (current <=mediaPlayer.getDuration()) {
                                current = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(current);
                                sleep(80);
                            }
                            while (current >= mediaPlayer.getDuration()) {
                                current = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(current);
                                sleep(80);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                };
                thread.start();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if (position!=list.size()-1){
                    position++;
                }
                else {
                    position= 0;
                }
                name= list.get((position)).toString();
                textView.setText(name);
                Uri uri = Uri.parse(list.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
                thread = new Thread() {
                    @Override
                    public void run() {
                        int current = 0;
                        try {
                            while (current <=mediaPlayer.getDuration()) {
                                current = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(current);
                                sleep(80);
                            }
                            while (current >= mediaPlayer.getDuration()) {
                                current = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(current);
                                sleep(80);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                };
                thread.start();
            }

        });

    }
}
