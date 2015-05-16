package xyz.sunting.opengles.light.model.graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import xyz.sunting.opengles.light.model.TSApplication;
import xyz.sunting.opengles.light.model.util.TSShaderUtil;

/**
 * 球
 */
public class Ball extends Sprite {
    static final float UNIT_SIZE = 1.0f;
    static final float DEFAULT_COLOR = 0.5f;
    static final float DEFAULT_RADIUS = 0.5f;

    int mProgram;
    int mMVPMatrixHandle;
    int mPositionHandle;
    int mColorHandle;

    FloatBuffer mVertexBuffer;
    int vCount = 0;

    float mRadius;
    float mColor;

    public Ball() {
        this(DEFAULT_RADIUS, DEFAULT_COLOR);
    }

    public Ball(float radius, float color) {
        super();
        mRadius = radius;

        initVertexData();
        initShader();
    }

    private void initVertexData() {
        ArrayList<Float> alVertix = new ArrayList<Float>();
        final int angleSpan = 5;
        for (int vAngle = -90; vAngle < 90; vAngle = vAngle + angleSpan) {
            for (int hAngle = 0; hAngle <= 360; hAngle = hAngle + angleSpan) {
                float x0 = (float) (mRadius * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math
                        .toRadians(hAngle)));
                float y0 = (float) (mRadius * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math
                        .toRadians(hAngle)));
                float z0 = (float) (mRadius * UNIT_SIZE * Math.sin(Math
                        .toRadians(vAngle)));

                float x1 = (float) (mRadius * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math
                        .toRadians(hAngle + angleSpan)));
                float y1 = (float) (mRadius * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math
                        .toRadians(hAngle + angleSpan)));
                float z1 = (float) (mRadius * UNIT_SIZE * Math.sin(Math
                        .toRadians(vAngle)));

                float x2 = (float) (mRadius * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
                        .cos(Math.toRadians(hAngle + angleSpan)));
                float y2 = (float) (mRadius * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
                        .sin(Math.toRadians(hAngle + angleSpan)));
                float z2 = (float) (mRadius * UNIT_SIZE * Math.sin(Math
                        .toRadians(vAngle + angleSpan)));

                float x3 = (float) (mRadius * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
                        .cos(Math.toRadians(hAngle)));
                float y3 = (float) (mRadius * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
                        .sin(Math.toRadians(hAngle)));
                float z3 = (float) (mRadius * UNIT_SIZE * Math.sin(Math
                        .toRadians(vAngle + angleSpan)));

                // 将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
                alVertix.add(x0);
                alVertix.add(y0);
                alVertix.add(z0);

                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);

                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);


                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);

                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);

                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
            }
        }

        vCount = alVertix.size() / 3;

        float vertices[] = new float[vCount * 3];
        for (int i = 0; i < alVertix.size(); i++) {
            vertices[i] = alVertix.get(i);
        }

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
    }

    private void initShader() {
        String vertexShader = TSShaderUtil.loadFromAssetsFile("ball_vertex.glsl", TSApplication.getInstance().getResources());
        String fragmentShader = TSShaderUtil.loadFromAssetsFile("ball_frag.glsl", TSApplication.getInstance().getResources());
        mProgram = TSShaderUtil.createProgram(vertexShader, fragmentShader);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "u_Color");
    }

    public void drawSelf() {
        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mState.getMVPMatrix(), 0);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glUniform1f(mColorHandle, mColor);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
    }

}