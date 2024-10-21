package com.ByteMe;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

public class Slingshot {
    public Texture texture;
    public ArrayList<Integer> position;
    public ArrayList<Integer> size;

    public Slingshot(String t) {
        texture = new Texture(t);
        position = new ArrayList<>(2);
        size = new ArrayList<>(2);
        position.add(100);
        position.add(70);
        size.add(50);
        size.add(150);
    }
}

