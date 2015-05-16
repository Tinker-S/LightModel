package xyz.sunting.opengles.light.model.graphics;

import xyz.sunting.opengles.light.model.util.TSMatrixState;

public abstract class Sprite {

    protected TSMatrixState mState;

    public Sprite() {
        mState = new TSMatrixState();
    }

    public void drawSelf() {
    }

    public TSMatrixState getState() {
        return mState;
    }
}