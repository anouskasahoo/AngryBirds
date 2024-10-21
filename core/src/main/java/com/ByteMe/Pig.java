package com.ByteMe;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public abstract class Pig{
    public Texture texture;
    public ArrayList<Integer> position;
    public ArrayList<Integer> size;
    public int health;

    public Pig(String t, int health) {
        this.health = health;
        texture = new Texture(t);
        position = new ArrayList<>(2);
        size = new ArrayList<>(2);
    }
}
