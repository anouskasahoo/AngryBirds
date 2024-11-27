package com.ByteMe;

import com.badlogic.gdx.graphics.Texture;

public class Bombird extends Bird {
    public transient Texture blastTexture  = new Texture("blast.png");
    public boolean hasExploded = false;

    public Bombird() {
        super("bombird.png"); // 3 damage
        size.add(75);
        size.add(75);
        //blastTexture
        damage = 3;
    }
}
