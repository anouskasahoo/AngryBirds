package com.ByteMe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TNT extends Obstacle {
    public Texture blastTexture;
    public boolean hasExploded = false;

    public TNT(Vector2 position) {
        super("tnt.png", position, 50, 50, 1); // 1 health
        blastTexture = new Texture("blast.png");
    }
}
