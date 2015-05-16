package xyz.sunting.opengles.light.model;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public class TSMainActivity extends AppCompatActivity {
    private static final int PADDING = 64;

    GLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGlSurfaceView = (GLSurfaceView) findViewById(R.id.surface);
        mGlSurfaceView.setEGLContextClientVersion(2);
        mGlSurfaceView.setRenderer(new TSRenderer());
        mGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams lp = mGlSurfaceView.getLayoutParams();
        lp.width = widthPixels - PADDING;
        lp.height = widthPixels - PADDING;
        mGlSurfaceView.setLayoutParams(lp);
    }

}