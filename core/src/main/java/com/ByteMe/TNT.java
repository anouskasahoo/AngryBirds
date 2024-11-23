package com.ByteMe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TNT extends Obstacle {
    public Texture blastTexture;
    public boolean hasExploded = false;
    private long explosionStartTime;
    public float blastTimer = 0;
    public static final float BLAST_DURATION = 1.5f;

    public TNT(Vector2 position) {
        super("tnt.png", position, 50, 50, 1); // 1 health
        blastTexture = new Texture("blast.png");
    }
}
