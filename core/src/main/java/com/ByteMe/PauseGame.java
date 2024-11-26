package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PauseGame implements Screen {

    private SpriteBatch batch;
    private Texture bgtexture;
    private Texture resumeButton;
    private Texture endButton;
    private Texture saveButton;
    private MainLauncher game;
    private final Player player;
    private int status;
    private GameState gameState;

    public PauseGame(MainLauncher game, int i, Player player, GameState gameState) {
        this.game=game;
        this.player = player;
        batch = new SpriteBatch();
        status = i;
        bgtexture = new Texture("pause_bg.png");
        resumeButton = new Texture("resume_button.png");
        endButton = new Texture("endgame_button.png");
        saveButton = new Texture("savegame_button.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int resume_x = 150;
        int resume_y = 180;
        int resume_width = 500;
        int resume_height = 60;
        int save_x = 150;
        int save_y = 105;
        int save_width = 240;
        int save_height = 55;
        int end_x = 410;
        int end_y = 105;
        int end_width = 240;
        int end_height = 55;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= resume_x && mouseX <= resume_x+resume_width && mouseY >= resume_y && mouseY <= resume_y+resume_height) {
                /*
                switch (status){
                    case 0:
                        //game.setScreen(new LoadedGame(game, player));
                        break;
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
                 */
                //resume
            }
            if (mouseX >= save_x && mouseX <= save_x+save_width && mouseY >= save_y && mouseY <= save_y+save_height) {
                game.setScreen(new LevelsScreen(game, player));
                //end game
            }
            if (mouseX >= end_x && mouseX <= end_x+end_width && mouseY >= end_y && mouseY <= end_y+end_height) {
                System.out.println("Pause trying");
                saveGame(gameState, player);
                System.out.println("Pause success");
                //save game
            }

        }

        batch.begin();
        batch.draw(bgtexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(resumeButton, resume_x, resume_y, resume_width, resume_height);
        batch.draw(saveButton, save_x, save_y, save_width, save_height);
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

    public static void saveGame(GameState gameState, Player player) {
        try (FileOutputStream fileOut = new FileOutputStream("saved.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            System.out.println("Save trying");
            out.writeObject(gameState);
            System.out.println("Game state saved to saved.ser");

            // Update player's loaded game
            player.setLoadedGame(gameState);
            System.out.println("Player's loaded game updated.");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

}
