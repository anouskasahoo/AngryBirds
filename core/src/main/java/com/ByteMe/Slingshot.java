package com.ByteMe;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

public class Slingshot {
    public Texture texture = new Texture("slingshot1.png");
    public ArrayList<Integer> position;
    public ArrayList<Integer> size;

    public Slingshot(String t, int p1, int p2, int s1, int s2) {
        texture = new Texture(t);
        position = new ArrayList<>(2);
        size = new ArrayList<>(2);
        position.add(p1);
        position.add(p2);
        size.add(s1);
        size.add(s2);
    }
}

