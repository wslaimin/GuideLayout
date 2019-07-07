package com.lm.guidelayout.example;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.lm.guidelayout.DecorSet;
import com.lm.guidelayout.GuideLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GuideLayout guide=findViewById(R.id.guide);
        guide.addFrame(R.layout.frame_a);
        guide.addFrame(R.layout.frame_b);
        guide.addFrame(R.layout.frame_c);
        guide.addFrame(R.layout.frame_d);
        guide.addFrame(R.layout.frame_e);


        View text=findViewById(R.id.text0);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Hello World",Toast.LENGTH_LONG).show();
            }
        });
        guide.getFrame(0).setAnchorView(text);
        guide.getFrame(0).findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide.toFrame(guide.getFrameIndex()+1);
            }
        });
        guide.getFrame(1).setAnchorView(findViewById(R.id.text1));
        guide.getFrame(1).findViewById(R.id.image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide.toFrame(guide.getFrameIndex()+1);
            }
        });
        guide.getFrame(2).setAnchorView(findViewById(R.id.text2));
        guide.getFrame(2).findViewById(R.id.image2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide.toFrame(3);
            }
        });
        guide.getFrame(3).setAnchorView(findViewById(R.id.text3));
        guide.getFrame(3).findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide.toFrame(4);
            }
        });

        guide.getFrame(4).setAnchorRect(new Rect(800,800,1000,1000));
        guide.getFrame(4).findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide.dismiss();
            }
        });

        DecorSet decorSet=new DecorSet();
        decorSet.addDecor(new StarDecor());
        decorSet.addDecor(new ImageDecor(this));
        guide.getFrame(3).setDrawDecor(decorSet);

        guide.toFrame(0);
    }
}
