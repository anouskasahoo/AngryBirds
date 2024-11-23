package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.ByteMe.MainLauncher.levelsBg;

public class HandScreen implements Screen {
    private SpriteBatch batch;
    private Texture bgtexture;
    private Texture backButton;
    private Texture playButton;
    private MainLauncher game;
    private int level;
    private final Player player;

    public HandScreen(MainLauncher game, int level, Player player) {
        this.game=game;
        this.player = player;
        this.level=level;
        batch = new SpriteBatch();
        bgtexture = new Texture(levelsBg.get(level));
        backButton = new Texture("backbutton.png");
        playButton = new Texture("PlayButton.png");
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

        int play_x = 490;
        int play_y = 10;
        int play_width = 150;
        int play_height = 50;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= back_x && mouseX <= back_x+back_width && mouseY >= back_y && mouseY <= back_y+back_height) {
                game.setScreen(new LevelsScreen(game, player));
            }
            if (mouseX >= play_x && mouseX <= play_x+play_width && mouseY >= play_y && mouseY <= play_y+play_height) {
                switch(level) {
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
        }

        batch.begin();
        batch.draw(bgtexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(backButton, back_x, back_y, back_width, back_height);
        batch.draw(playButton, play_x, play_y, play_width, play_height);
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
