package com.ByteMe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bombird extends Bird {
    public Texture blastTexture;
    public boolean hasExploded = false;
    private long explosionStartTime;

    public Bombird() {
        super("bombird.png"); // 3 damage
        size.add(75);
        size.add(75);
        blastTexture = new Texture("blast.png");
        damage = 3;
    }
}
