package xyz.sunting.opengles.light.model.graphics;

import android.opengl.GLES20;

import xyz.sunting.opengles.light.model.TSApplication;
import xyz.sunting.opengles.light.model.util.TSMatrixState;
import xyz.sunting.opengles.light.model.util.TSShaderUtil;

public class Point extends Sprite {
    static final float DEFAULT_COLOR = 0.5f;
    static final float DEFAULT_SIZE = 20.0f;
    static final float[] DEFAULT_VERTICES = {0, 0, 0};

    private float mColor;
    private float mSize;
    private float[] mVertices;

    private int mProgram;
    private int mMVPMatrixHandle;
    private int mPositionHandle;
    private int mColorHandle;
    private int mSizeHandle;

    public Point() {
        this(DEFAULT_VERTICES, DEFAULT_COLOR, DEFAULT_SIZE);
    }

    public Point(float[] vertices, float color, float size) {
        super();

        mColor = color;
        mSize = size;
        mVertices = vertices;

        initShader();
    }

    private void initShader() {
        String vertexShader = TSShaderUtil.loadFromAssetsFile("point_vertex.glsl", TSApplication.getInstance().getResources());
        String fragmentShader = TSShaderUtil.loadFromAssetsFile("point_frag.glsl", TSApplication.getInstance().getResources());
        mProgram = TSShaderUtil.createProgram(vertexShader, fragmentShader);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "u_Color");
        mSizeHandle = GLES20.glGetUniformLocation(mProgram, "u_Size");

    }

    public void drawSelf() {
        GLES20.glUseProgram(mProgram);

        GLES20.glVertexAttrib3f(mPositionHandle, mVertices[0], mVertices[1], mVertices[2]);
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, TSMatrixState.getMVPMatrix(), 0);
        GLES20.glUniform1f(mColorHandle, mColor);
        GLES20.glUniform1f(mSizeHandle, mSize);

        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
    }
}