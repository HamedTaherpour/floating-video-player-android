package ir.hamedt.floatingvideoplayer;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.VideoView;

import io.hamed.floatinglayout.FloatingLayout;
import io.hamed.floatinglayout.callback.FloatingCallBack;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingLayout floatingLayout;
    private VideoView videoView;

    private FloatingCallBack floatingCallBack = new FloatingCallBack() {
        @Override
        public void onCreateListener(View view) {
            initVideoView(view);
            view.findViewById(R.id.btn_close).setOnClickListener(MainActivity.this);
        }

        @Override
        public void onCloseListener() {
            videoView.stopPlayback();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_run).setOnClickListener(this);
    }

    private void initVideoView(View view) {
        videoView = view.findViewById(R.id.video_player);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.matrix);
        videoView.setVideoURI(uri);
        videoView.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_run:
                if (!isNeedPermission())
                    showFloating();
                else
                    requestPermission();
                break;
            case R.id.btn_close:
                floatingLayout.close();
                break;
        }
    }

    private boolean isNeedPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this);
    }

    private void requestPermission() {
        Intent intent = new Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName())
        );
        startActivityForResult(intent, 25);
    }

    private void showFloating() {
        floatingLayout = new FloatingLayout(getApplicationContext(), R.layout.floating_layout, floatingCallBack);
        floatingLayout.create();
    }
}
