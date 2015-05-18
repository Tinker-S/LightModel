package xyz.sunting.opengles.light.model.util;

import android.opengl.Matrix;
import android.util.Log;

/**
 * 矩阵变换工具类
 *
 * @author tinker <sunting.bcwl@gmail.com>
 */
public class TSMatrixState {
    private static final int MATRIX_F4V_SIZE = 16;
    private static final int MAX_STACK_SIZE = 32;

    /**
     * 光源位置
     */
    public static float[] mLightPosition = new float[]{0, 0, 0};
    /**
     * 光源方向
     */
    public static float[] mLightDirection = new float[]{0, 0, 0};
    /**
     * 模型矩阵
     */
    private static float[] mModelMatrix = new float[MATRIX_F4V_SIZE];
    /**
     * 视角矩阵(Camera)
     */
    private static float[] mViewMatrix = new float[MATRIX_F4V_SIZE];
    /**
     * 模型视角矩阵
     */
    private static float[] mViewModelMatrix = new float[MATRIX_F4V_SIZE];
    /**
     * 投影矩阵
     */
    private static float[] mProjectionMatrix = new float[MATRIX_F4V_SIZE];
    /**
     * 经过模型-视角-投影变换后传入shader中的最终矩阵
     */
    private static float[] mMVPMatrix = new float[MATRIX_F4V_SIZE];

    /**
     * 保存矩阵状态的栈
     */
    private static float[][] mStack = new float[MAX_STACK_SIZE][MATRIX_F4V_SIZE];
    private static int mStackTop = -1;

    public static void init() {
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setIdentityM(mProjectionMatrix, 0);
    }

    public static void push() {
        mStackTop++;
        for (int i = 0; i < 16; i++) {
            mStack[mStackTop][i] = mModelMatrix[i];
        }
    }

    public static void pop() {
        for (int i = 0; i < 16; i++) {
            mModelMatrix[i] = mStack[mStackTop][i];
        }
        mStackTop--;
    }

    public static void logMatrix(float[] matrix) {
        String m = "";
        for (int i = 0; i < matrix.length; i++) {
            m = m + matrix[i] + " ";
        }
        Log.d("Matrix", m);
    }

    public static void translate(float x, float y, float z) {
        Matrix.translateM(mModelMatrix, 0, x, y, z);
    }

    public static void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(mModelMatrix, 0, angle, x, y, z);
    }

    public static void scale(float x, float y, float z) {
        Matrix.scaleM(mModelMatrix, 0, x, y, z);
    }

    public static void setLookAtM(float cx, float cy, float cz, float tx, float ty, float tz,
                                  float upx, float upy, float upz) {
        Matrix.setLookAtM(mViewMatrix, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz);
    }

    public static void frustumM(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public static void orthoM(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public static float[] getProjectionMatrix() {
        return mProjectionMatrix;
    }

    public static float[] getViewMatrix() {
        return mViewMatrix;
    }

    public static float[] getModelMatrix() {
        return mModelMatrix;
    }

    public static float[] getViewModelMatrix() {
        Matrix.multiplyMM(mViewModelMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        return mViewModelMatrix;
    }

    public static float[] getMVPMatrix() {
        Matrix.multiplyMM(mViewModelMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewModelMatrix, 0);
        return mMVPMatrix;
    }

    public static void setLightPosition(float x, float y, float z) {
        mLightPosition[0] = x;
        mLightPosition[1] = y;
        mLightPosition[2] = z;
    }

    public static void setLightDirection(float x, float y, float z) {
        mLightDirection[0] = x;
        mLightDirection[1] = y;
        mLightDirection[2] = z;
    }

}