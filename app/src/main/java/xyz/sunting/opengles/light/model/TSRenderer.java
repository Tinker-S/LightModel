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

    Sprite mPoint;
    Sprite mTriangle;
    Sprite mBall;
    Sprite mRectangle;

    float mAngle;

    public TSRenderer() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 0);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        mTriangle = new Triangle();
        mRectangle = new Rectangle();
        mPoint = new Point();
        mBall = new Ball();

        TSMatrixState.init();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mAngle = (mAngle + 2f) % 360;
                    System.out.println(mAngle);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        //TSMatrixState.frustumM(-ratio, ratio, -1, 1, 1f, 10);
        //TSMatrixState.setLookAtM(0, 0, 5, 0, 0, -1, 0, 1, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        TSMatrixState.push();
        //TSMatrixState.rotate(mAngle, 0, 1, 0);
        mBall.drawSelf();
        TSMatrixState.pop();
    }

}