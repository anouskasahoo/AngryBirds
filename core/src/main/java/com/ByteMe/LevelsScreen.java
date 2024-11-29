package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelsScreen implements Screen {
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private MainLauncher game;
    private final Texture Level1;
    private final Texture Level2;
    private final Texture Level3;
    private final Texture backButton;
    private final Player player;


    public LevelsScreen (MainLauncher game, Player player) {
        this.game = game;
        this.player = player;
        batch = new SpriteBatch();
        backgroundTexture = new Texture("levels_bg.png");
        Level1 = new Texture("Level1.png");
        Level2 = new Texture("Level2.png");
        Level3 = new Texture("Level3.png");
        backButton = new Texture("backbutton.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int level1_x = 278;
        int level1_y = 50;
        int level1_w = 65;
        int level1_h = 375;

        int level2_x = 361;
        int level2_y = 45;
        int level2_w = 60;
        int level2_h = 380;

        int level3_x = 443;
        int level3_y = 50;
        int level3_w = 60;
        int level3_h = 375;

        int level4_x = 525;
        int level4_y = 50;
        int level4_w = 60;
        int level4_h = 375;

        int level5_x = 608;
        int level5_y = 50;
        int level5_w = 60;
        int level5_h = 375;

        int level6_x = 690;
        int level6_y = 50;
        int level6_w = 60;
        int level6_h = 375;

        int back_x = 10;
        int back_y = 400;
        int back_width = 80;
        int back_height = 50;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= level1_x && mouseX <= level1_x+level1_w&& mouseY >= level1_y && mouseY <= level1_y+level1_h) {
                game.setScreen(new HandScreen(game,1, player));
            }
            if (mouseX >= level2_x && mouseX <= level2_x+level2_w&& mouseY >= level2_y && mouseY <= level2_y+level2_h) {
                game.setScreen(new HandScreen(game,2, player));
            }
            if (mouseX >= level3_x && mouseX <= level3_x+level3_w&& mouseY >= level3_y && mouseY <= level3_y+level3_h) {
                game.setScreen(new HandScreen(game,3, player));
            }
            if (mouseX >= level4_x && mouseX <= level4_x+level4_w&& mouseY >= level4_y && mouseY <= level4_y+level4_h) {
                game.setScreen(new HandScreen(game,4, player));
                //game.setScreen(new NewLevel4(game, player, false, null));
            }
            if (mouseX >= level5_x && mouseX <= level5_x+level5_w&& mouseY >= level5_y && mouseY <= level5_y+level5_h) {
                game.setScreen(new HandScreen(game,5, player));
                //game.setScreen(new NewLevel5(game, player, false, null));
            }
            if (mouseX >= level6_x && mouseX <= level6_x+level6_w&& mouseY >= level6_y && mouseY <= level6_y+level6_h) {
                game.setScreen(new HandScreen(game,6, player));
                //game.setScreen(new NewLevel6(game, player, false, null));
            }
            if (mouseX >= back_x && mouseX <= back_x+back_width && mouseY >= back_y && mouseY <= back_y+back_height) {
                game.setScreen(new HomeScreen(game, player));
            }

        }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(Level1, 278, 50, 65, 375);
        batch.draw(Level2, 361, 45, 60, 380);
        batch.draw(Level3, 443, 50, 60, 375);
        batch.draw(backButton, back_x, back_y, back_width, back_height);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
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
        batch.dispose();
        backgroundTexture.dispose(); // Clean up resources
    }
}
