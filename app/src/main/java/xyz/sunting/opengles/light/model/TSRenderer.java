package xyz.sunting.opengles.light.model;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import xyz.sunting.opengles.light.model.graphics.Ball;
import xyz.sunting.opengles.light.model.graphics.Point;
import xyz.sunting.opengles.light.model.graphics.Rectangle;
import xyz.sunting.opengles.light.model.graphics.Sprite;
import xyz.sunting.opengles.light.model.graphics.Triangle;
import xyz.sunting.opengles.light.model.util.TSMatrixState;

public class TSRenderer implements GLSurfaceView.Renderer {

    Sprite mSprite;

    public TSRenderer() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 0);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND);

        // mSprite = new Triangle();
        // mSprite = new Rectangle();
        // mSprite = new Ball();
        mSprite = new Point();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        //mState.frustumM(-ratio, ratio, -1, 1, 0.1f, 10);

        TSMatrixState.logMatrix(mSprite.getState().getMVPMatrix());
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        mSprite.drawSelf();
    }
}
