package ir.hamedt.floatingvideoplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.hamedtaherpour.floatinglayout.FloatingLayout;

public class MainActivity extends AppCompatActivity implements FloatingLayout.CallBack {

    private FloatingLayout floatingLayout;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingLayout = new FloatingLayout(getApplicationContext(), R.layout.floating_layout, MainActivity.this);
        ((Button) findViewById(R.id.btn_run)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!floatingLayout.isShow())
                    floatingLayout.create();
            }
        });
    }

    @Override
    public void onClickListener(int resource) {
        if (resource == R.id.btn_close)
            floatingLayout.close();
    }

    @Override
    public void onCreateListener(View view) {
        videoView = view.findViewById(R.id.video_player);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.matrix);
        videoView.setVideoURI(uri);
        videoView.start();
    }

    @Override
    public void onCloseListener() {
        videoView.stopPlayback();
    }
}
