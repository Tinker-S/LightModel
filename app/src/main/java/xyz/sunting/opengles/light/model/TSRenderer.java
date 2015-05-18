package xyz.sunting.opengles.light.model;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import xyz.sunting.opengles.light.model.graphics.Ball;
import xyz.sunting.opengles.light.model.graphics.LightBall;
import xyz.sunting.opengles.light.model.graphics.Point;
import xyz.sunting.opengles.light.model.graphics.Rectangle;
import xyz.sunting.opengles.light.model.graphics.Sprite;
import xyz.sunting.opengles.light.model.graphics.Triangle;
import xyz.sunting.opengles.light.model.util.TSMatrixState;

public class TSRenderer implements GLSurfaceView.Renderer {

    float[] mLightModelMatrix = new float[16];

    Sprite mPoint;
    Sprite mTriangle;
    Sprite mBall;
    Sprite mRectangle;
    Sprite mLightBall;

    float mAngle;
    float mLightAngle;

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
        mLightBall = new LightBall();

        TSMatrixState.init();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mAngle = (mAngle + 2f) % 360;

                    setLightPosition(-mAngle);

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
        TSMatrixState.rotate(mAngle, 0, 1, 0);
        // mBall.drawSelf();
        mLightBall.drawSelf();
        TSMatrixState.pop();
    }

    /**
     * 按照规律移动光源位置
     *
     * @param angle
     */
    private void setLightPosition(float angle) {
        float[] lightModelInWorldSpace = new float[] { 0, 0, 0, 1 };

        Matrix.setIdentityM(mLightModelMatrix, 0);
        Matrix.rotateM(mLightModelMatrix, 0, angle, 1, 1, 0);
        Matrix.translateM(mLightModelMatrix, 0, -4, 4, 4);

        Matrix.multiplyMV(lightModelInWorldSpace, 0, mLightModelMatrix, 0, lightModelInWorldSpace, 0);
        TSMatrixState.setLightPosition(lightModelInWorldSpace[0], lightModelInWorldSpace[1], lightModelInWorldSpace[2]);
    }
}