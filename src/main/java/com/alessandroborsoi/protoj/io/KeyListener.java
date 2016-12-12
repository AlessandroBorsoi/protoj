package com.alessandroborsoi.protoj.io;

public class KeyListener {
    private int keyMonitored;
    private boolean keyMonitoredWasPressed;

    public void setKeyMonitored(int keyMonitored) {
        this.keyMonitored = keyMonitored;
    }

    // This is triggered exactly when the key is pressed.
    public void onKeyDown() {
    }

    // This is triggered will the key is being pressed.
    public void keyPressed() {
    }

    // This is triggered exactly when the key is released.
    public void onKeyUp() {
    }

    public void checkKey() {
        if (KeyboardHandler.isKeyDown(keyMonitored)) {
            if (keyMonitoredWasPressed) {
                keyPressed();
            } else {
                keyMonitoredWasPressed = true;
                onKeyDown();
            }
        } else if (keyMonitoredWasPressed) {
            onKeyUp();
            keyMonitoredWasPressed = false;
        }
    }
}
