package com.ByteMe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Bird implements Serializable {
    private static final long serialVersionUID = 1L;

    public transient Texture texture;
    public ArrayList<Integer> size;
    public Vector2 position;
    Vector2 velocity;
    boolean isFlying;
    public ArrayList<Vector2> trajectoryPoints;
    public int damage;
    public float launchVelocityX;
    public float launchVelocityY;

    public Bird(String t) {
        texture = new Texture(t);
        size = new ArrayList<>(2);
        this.position = new Vector2();
        this.velocity = new Vector2(0, 0);
        this.isFlying = false;
        this.trajectoryPoints = new ArrayList<>();
        this.damage = damage;
    }

    public void setPosition(int screenX, int screenY) {
        position.set(screenX, screenY);
    }

    public void launch() {
        isFlying = true;
        velocity.set(launchVelocityX, launchVelocityY);
    }
}
