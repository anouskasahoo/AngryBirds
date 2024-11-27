package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Win implements Screen {

    private SpriteBatch batch;
    private Texture bgtexture = new Texture("win_bg.png");
    private Texture nextButton;
    private Texture endButton;
    private MainLauncher game;
    private final Player player;

    public Win(MainLauncher game, Player player) {
        this.game=game;
        this.player = player;
        batch = new SpriteBatch();
        bgtexture = new Texture("win_bg.png");
        nextButton = new Texture("next_wl.png");
        endButton = new Texture("exit_wl.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int next_x = 225;
        int next_y = 200;
        int next_width = 350;
        int next_height = 70;
        int end_x = 225;
        int end_y = 130;
        int end_width = 350;
        int end_height = 70;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= next_x && mouseX <= next_x+next_width && mouseY >= next_y && mouseY <= next_y+next_height) {
                game.setScreen(new LevelsScreen(game, player));
            }
            if (mouseX >= end_x && mouseX <= end_x+end_width && mouseY >= end_y && mouseY <= end_y+end_height) {
                game.setScreen(new HomeScreen(game, player));
            }

        }

        batch.begin();
        batch.draw(bgtexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(nextButton, next_x, next_y, next_width, next_height);
        batch.draw(endButton, end_x, end_y, end_width, end_height);
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
