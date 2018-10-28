package example.guidelayout.lm.com.guidelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lm.guidelayout.ClickAnchorListener;
import com.lm.guidelayout.GuideLayout;

public class MainActivity extends AppCompatActivity {
    GuideLayout guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guide=findViewById(R.id.guide);
        guide.getFrame(0).setAnchorView(findViewById(R.id.text0));
        guide.setBackgroundColor(0x60000000);
        guide.getFrame(0).setClickAnchorListener(new ClickAnchorListener() {
            @Override
            public void clickAnchor() {
                guide.nextFrame(1);
            }
        });
        guide.getFrame(1).setAnchorView(findViewById(R.id.text1));
        guide.getFrame(1).setClickAnchorListener(new ClickAnchorListener() {
            @Override
            public void clickAnchor() {
                guide.nextFrame(2);
            }
        });
        guide.getFrame(2).setAnchorView(findViewById(R.id.text2));
        guide.getFrame(2).setClickAnchorListener(new ClickAnchorListener() {
            @Override
            public void clickAnchor() {
                guide.dismissGuide();
            }
        });
        guide.nextFrame(0);
    }
}
