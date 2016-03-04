package xyz.sunting.opengles.light.model.graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import xyz.sunting.opengles.light.model.TSApplication;
import xyz.sunting.opengles.light.model.util.TSMatrixState;
import xyz.sunting.opengles.light.model.util.TSShaderUtil;

/**
 * 立方体
 */
public class Cube extends Sprite {
    private static final int BYTES_PER_FLOAT = 4;
    private static final int POSITION_DATA_SIZE = 3;
    private static final int COLOR_DATA_SIZE = 4;

    /**
     * Store our model data in a float buffer.
     */
    private FloatBuffer mCubePositions;
    private FloatBuffer mCubeColors;

    private int mProgram;
    private int mMVPMatrixHandle;
    private int mMVMatrixHandle;
    private int mPositionHandle;
    private int mColorHandle;

    public Cube() {
        this(0.5f);
    }

    public Cube(float factor) {
        initVertex(factor);
        initShader();
    }

    private void initVertex(float factor) {
        // X, Y, Z
        float[] cubePositionData = {
                // In OpenGL counter-clockwise winding is default. This means that when we look at a triangle,
                // if the points are counter-clockwise we are looking at the "front". If not we are looking at
                // the back. OpenGL has an optimization where all back-facing triangles are culled, since they
                // usually represent the backside of an object and aren't visible anyways.

                // Front face
                -1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,

                // Right face
                1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,

                // Back face
                1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f,

                // Left face
                -1.0f, 1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f,

                // Top face
                -1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f,

                // Bottom face
                1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
        };

        // R, G, B, A
        float[] cubeColorData = {
                // Front face (red)
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,

                // Right face (green)
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,

                // Back face (blue)
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,

                // Left face (yellow)
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,

                // Top face (cyan)
                0.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f,

                // Bottom face (magenta)
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f
        };

        for (int i = 0; i < cubePositionData.length; i++) {
            cubePositionData[i] = cubeColorData[i] * factor;
        }

        // Initialize the buffers.
        mCubePositions = ByteBuffer.allocateDirect(cubePositionData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubePositions.put(cubePositionData).position(0);

        mCubeColors = ByteBuffer.allocateDirect(cubeColorData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeColors.put(cubeColorData).position(0);
    }

    private void initShader() {
        String vertexShader = TSShaderUtil.loadFromAssetsFile("cube_vertex.glsl", TSApplication.getInstance().getResources());
        String fragmentShader = TSShaderUtil.loadFromAssetsFile("cube_frag.glsl", TSApplication.getInstance().getResources());
        mProgram = TSShaderUtil.createProgram(vertexShader, fragmentShader);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
        mMVMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "a_Color");
    }

    public void drawSelf() {
        GLES20.glUseProgram(mProgram);

        mCubePositions.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, POSITION_DATA_SIZE, GLES20.GL_FLOAT, false, 0, mCubePositions);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        mCubeColors.position(0);
        GLES20.glVertexAttribPointer(mColorHandle, COLOR_DATA_SIZE, GLES20.GL_FLOAT, false, 0, mCubeColors);
        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, TSMatrixState.getMVPMatrix(), 0);
        GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, TSMatrixState.getViewModelMatrix(), 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
    }
}
