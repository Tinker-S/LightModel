package xyz.sunting.opengles.light.model.graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import xyz.sunting.opengles.light.model.TSApplication;
import xyz.sunting.opengles.light.model.util.TSMatrixState;
import xyz.sunting.opengles.light.model.util.TSShaderUtil;

/**
 * 矩形
 */
public class Rectangle extends Sprite {
    static final float DEFAULT_COLOR = 0.5f;
    static final float[] DEFAULT_VERTICES = {
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    private FloatBuffer mVertices;
    private float mColor;

    private int mProgram;
    private int mMVPMatrixHandle;
    private int mPositionHandle;
    private int mColorHandle;

    private final int mBytesPerFloat = 4;

    public Rectangle() {
        this(DEFAULT_VERTICES, DEFAULT_COLOR);
    }

    public Rectangle(float[] vertices, float color) {
        super();

        mColor = color;
        mVertices = ByteBuffer.allocateDirect(vertices.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices.put(vertices).position(0);

        initShader();
    }

    private void initShader() {
        String vertexShader = TSShaderUtil.loadFromAssetsFile("rectangle_vertex.glsl", TSApplication.getInstance().getResources());
        String fragmentShader = TSShaderUtil.loadFromAssetsFile("rectangle_frag.glsl", TSApplication.getInstance().getResources());
        mProgram = TSShaderUtil.createProgram(vertexShader, fragmentShader);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "u_Color");
    }

    public void drawSelf() {
        GLES20.glUseProgram(mProgram);

        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 3 * mBytesPerFloat, mVertices);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, TSMatrixState.getMVPMatrix(), 0);
        GLES20.glUniform1f(mColorHandle, mColor);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
