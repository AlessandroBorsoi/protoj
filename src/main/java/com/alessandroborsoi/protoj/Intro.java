package com.alessandroborsoi.protoj;

import com.alessandroborsoi.protoj.entity.impl.Planet;
import com.alessandroborsoi.protoj.entity.impl.Text;
import com.alessandroborsoi.protoj.io.EventManager;
import com.alessandroborsoi.protoj.io.KeyListener;
import com.alessandroborsoi.protoj.io.WindowManager;
import com.alessandroborsoi.protoj.util.Time;
import com.alessandroborsoi.protoj.util.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class Intro {
    private Layer layer;
    private ProtoJ protoJ;
    boolean introOn = true;

    public Intro(ProtoJ protoJ) {
        this.protoJ = protoJ;
        layer = new Layer();
        Planet planet = new Planet();
        planet.spawn(new Vector2f(320, 0), new Vector2f(7f * -1, 0), ProtoJ.getBackground());
        Text title = new Text(" . ProtoJ .");
        Text commandLabel = new Text("- Commands -");
        Text commandLabel0 = new Text("P            : Pause.");
        Text commandLabel1 = new Text("F1           : Start homing missile.");
        Text commandLabel2 = new Text("F2           : Start enemy waves.");
        Text commandLabel3 = new Text("X            : Detach/Move Orb.");
        Text commandLabel4 = new Text("Arrow key    : Move.");
        Text commandLabel5 = new Text("Space        : Fire ( maintain to charge).");
        Text commandLabel6 = new Text("Press SPACE to Start !!");
        Vector2f immobile = new Vector2f(0, 0);
        int pointY = 240;
        int pointX = -270;
        int interspace = 20;
        title.spawn(new Vector2f(pointX + 160, pointY -= interspace * 4), immobile, layer);
        commandLabel.spawn(new Vector2f(pointX + 200, pointY -= interspace * 4), immobile, layer);
        commandLabel0.spawn(new Vector2f(pointX, pointY -= interspace * 2), immobile, layer);
        commandLabel1.spawn(new Vector2f(pointX, pointY -= interspace), immobile, layer);
        commandLabel2.spawn(new Vector2f(pointX, pointY -= interspace), immobile, layer);
        commandLabel3.spawn(new Vector2f(pointX, pointY -= interspace), immobile, layer);
        commandLabel4.spawn(new Vector2f(pointX, pointY -= interspace), immobile, layer);
        commandLabel5.spawn(new Vector2f(pointX, pointY -= interspace), immobile, layer);
        commandLabel6.spawn(new Vector2f(pointX + 150, pointY -= interspace * 3), immobile, layer);
        KeyListener space = new KeyListener() {
            public void onKeyUp() {
                introOn = false;
                Time.getInstance().resume();
            }
        };
        EventManager.getInstance().addListener(GLFW_KEY_SPACE, space);
    }

    public void play() {
        Time.getInstance().pause();
        while (introOn) {
            protoJ.update();
            protoJ.render();
            layer.render();
            WindowManager.render();
//            if (protoJ.exitRequested()) {
//                introOn = false;
//                protoJ.gameOff = true;
//            }
            EventManager.getInstance().checkEvents();
        }
        EventManager.getInstance().clear();
    }
}
