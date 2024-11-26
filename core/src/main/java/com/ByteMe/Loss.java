package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Loss implements Screen {

    private SpriteBatch batch;
    private Texture bgtexture;
    private Texture nextButton;
    private Texture endButton;
    private MainLauncher game;
    private final Player player;
    private int status;

    public Loss(MainLauncher game, int i, Player player) {
        this.game=game;
        this.player = player;
        status=i;
        batch = new SpriteBatch();
        bgtexture = new Texture("loss_bg.png");
        nextButton = new Texture("restart_wl.png");
        endButton = new Texture("exit_wl.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int restart_x = 225;
        int restart_y = 200;
        int restart_width = 350;
        int restart_height = 70;
        int end_x = 225;
        int end_y = 130;
        int end_width = 350;
        int end_height = 70;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= restart_x && mouseX <= restart_x+restart_width && mouseY >= restart_y && mouseY <= restart_y+restart_height) {
                switch (status){
                    case 1:
                        game.setScreen(new Level1(game, player));
                        break;
                    case 2:
                        //game.setScreen(new Level2(game, player));
                        break;
                    case 3:
                        //game.setScreen(new Level3(game, player));
                        break;
                }
            }
            if (mouseX >= end_x && mouseX <= end_x+end_width && mouseY >= end_y && mouseY <= end_y+end_height) {
                game.setScreen(new HomeScreen(game, player));
            }

        }

        batch.begin();
        batch.draw(bgtexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(nextButton, restart_x, restart_y, restart_width, restart_height);
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
