package com.alessandroborsoi.protoj.io;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static EventManager instance;
    private List<KeyListener> listeners = new ArrayList<>();

    private EventManager() {
    }

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void addListener(int key, KeyListener listener) {
        listener.setKeyMonitored(key);
        listeners.add(listener);
    }

    public void removeListener(KeyListener listener) {
        listeners.remove(listener);
    }

    public void clear() {
        listeners.clear();
    }

    public void checkEvents() {
        listeners.forEach(KeyListener::checkKey);
    }
}
