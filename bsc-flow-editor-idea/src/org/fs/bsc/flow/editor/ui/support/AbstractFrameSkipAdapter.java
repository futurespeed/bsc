package org.fs.bsc.flow.editor.ui.support;

public abstract class AbstractFrameSkipAdapter {

    private long lastStamp = System.currentTimeMillis();
    private int fps = 60;

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public final void go(){
        long currStamp = System.currentTimeMillis();
        long duration = currStamp - lastStamp;
        if(duration > 0 && (1000 / duration <= fps)){
            doGo();
            lastStamp = currStamp;
        }
    }

    protected abstract void doGo();
}
