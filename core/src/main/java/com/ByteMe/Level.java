package com.ByteMe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public abstract class Level {
    protected final MainLauncher game;
    protected final SpriteBatch batch;
    protected final Texture pauseButton;
    protected final Slingshot slingshot;
    protected ArrayList<Bird> birds;
    protected ArrayList<Pig> pigs;
    public int pauseButton_x = 720;
    public int pauseButton_y = 420;
    public int pauseButton_w = 75;
    public int pauseButton_h = 50;

    // Background texture and slingshot2 are now protected to allow access in derived classes
    protected Texture backgroundTexture;
    protected Texture slingshot2;

    public Level(MainLauncher game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.slingshot = new Slingshot("slingshot1.png");
        this.birds = new ArrayList<>();
        this.pauseButton = new Texture("pausebutton.png");
    }

    public void dispose() {
        batch.dispose();
        slingshot.texture.dispose(); // Dispose the slingshot texture
        if (slingshot2 != null) {
            slingshot2.dispose(); // Dispose slingshot2 texture
        }
        if (backgroundTexture != null) {
            backgroundTexture.dispose(); // Dispose background texture
        }
        for (Bird bird : birds) {
            if (bird.texture != null) {
                bird.texture.dispose(); // Dispose each bird's texture
            }
        }
    }
}
