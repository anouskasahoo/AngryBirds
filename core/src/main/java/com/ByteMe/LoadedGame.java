package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class LoadedGame extends Level implements Screen {
    private transient Texture bgtexture;// Image for the background
    private Player player;
    private MainLauncher game;
    private transient Texture playButton = new Texture("play_yellow.png");
    private transient Texture exitButton = new Texture("exit_yellow.png");

    private GameState gameState = null;

    public LoadedGame(MainLauncher game, Player player, boolean load) {
        super(game, "slingshot1.png", 100, 70, 50, 150, player);
        this.game = game;
        this.player = player;
        this.gameState = player.getLoadedGame();

        if (load) {
            if (gameState == null) {
                bgtexture = new Texture("no_loaded_bg.png");
            } else {
                bgtexture = new Texture("yes_loaded_bg.png");
            }
        }
        else{
            bgtexture = new Texture("winnerbg.png");
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float exitButton_x = 50;
        float exitButton_y = 50;
        float exitButton_h = 60;
        float exitButton_w = 120;

        float playButton_x = 630;
        float playButton_y = 50;
        float playButton_h = 60;
        float playButton_w = 120;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();


            if (mouseX >= exitButton_x && mouseX <= exitButton_x+exitButton_w && mouseY >= exitButton_y && mouseY <= exitButton_y+exitButton_h){
                game.setScreen(new HomeScreen(game, player));
            }
            if (mouseX >= playButton_x && mouseX <= playButton_x+playButton_w && mouseY >= playButton_y && mouseY <= playButton_y+playButton_h){
                if (gameState == null) {
                    game.setScreen(new LevelsScreen(game, player));
                }
                if (gameState!=null){
                    game.setScreen(new Level1(game, player, true, null)); //ISSUE1: SWITCH CASE FOR ALL LEVELS
                }
            }

        }
        batch.begin();
        batch.draw(bgtexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(exitButton, exitButton_x, exitButton_y, exitButton_w, exitButton_h);
        batch.draw(playButton, playButton_x, playButton_y, playButton_w, playButton_h);
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
