package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LeaderboardScreen implements Screen {
    private SpriteBatch batch;
    private Texture bgtexture;
    private Texture backButton;
    private MainLauncher game;

    public LeaderboardScreen(MainLauncher game) {
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
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        int back_x = 10;
        int back_y = 400;
        int back_width = 80;
        int back_height = 50;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= back_x && mouseX <= back_x+back_width && mouseY >= back_y && mouseY <= back_y+back_height) {
                game.setScreen(new HomeScreen(game));
            }
        }

        batch.begin();
        batch.draw(bgtexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(backButton, back_x, back_y, back_width, back_height);
        batch.end();
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
