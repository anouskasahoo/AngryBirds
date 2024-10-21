package com.ByteMe;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseGame implements Screen {

    private SpriteBatch batch;
    private Texture bgtexture;
    private Texture backButton;
    private MainLauncher game;

    public PauseGame(MainLauncher game) {
        this.game=game;
        batch = new SpriteBatch();
        bgtexture = new Texture("Leaderboard_bg.png");
        backButton = new Texture("backbutton.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
