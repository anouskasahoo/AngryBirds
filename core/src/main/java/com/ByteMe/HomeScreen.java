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

import static com.ByteMe.MainLauncher.players;

public class HomeScreen implements Screen {
    private transient SpriteBatch batch;
    private transient Texture backgroundTexture;
    private MainLauncher game;
    private final Player player;
    private transient Texture exitButton;
    private transient Texture loadButton;
    private transient Texture boardButton;
    private transient Texture playButton;
    private transient Texture tutButton;

    public HomeScreen(MainLauncher game, Player player) {
        this.game = game;
        this.player = player;
        batch = new SpriteBatch();
        backgroundTexture = new Texture("homebg.png");
        exitButton = new Texture("home_exitbutton.png");
        loadButton = new Texture("home_loadbutton.png");
        boardButton = new Texture("leaderboard.png");
        tutButton = new Texture("tut_button.png");
        playButton = new Texture("home_playbutton.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int exit_x = 550;
        int exit_y = 35;
        int exit_width = 250;
        int exit_height = 150;
        int load_x = 15;
        int load_y = 35;
        int load_width = 250;
        int load_height = 150;
        int board_x = 610;
        int board_y = 300;
        int board_width = 150;
        int board_height = 100;
        int tut_x = 25;
        int tut_y = 300;
        int tut_width = 180;
        int tut_height = 120;
        int play_x = 250;
        int play_y = 75;
        int play_width = 330;
        int play_height = 200;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= exit_x && mouseX <= exit_x+exit_width && mouseY >= exit_y && mouseY <= exit_y+exit_height) {
                savePlayers();
                Gdx.app.exit();
            }
            if (mouseX >= load_x && mouseX <= load_x+load_width && mouseY >= load_y && mouseY <= load_y+load_height) {
                game.setScreen(new LoadedGame(game, player, true));
            }
            if (mouseX >= tut_x && mouseX <= tut_x + tut_width && mouseY >= tut_y && mouseY <= tut_y + tut_height) {
                game.setScreen(new LeaderboardScreen(game, player, "tutorial.png"));
            }
            if (mouseX >= board_x && mouseX <= board_x+board_width && mouseY >= board_y && mouseY <= board_y+board_height) {
                game.setScreen(new LeaderboardScreen(game, player, "Leaderboard_bg.png"));
            }
            if (mouseX >= play_x && mouseX <= play_x+play_width && mouseY >= play_y && mouseY <= play_y+play_height) {
                game.setScreen(new LevelsScreen (game, player));
            }

        }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(exitButton, exit_x, exit_y, exit_width, exit_height);
        batch.draw(loadButton, load_x, load_y, load_width, load_height);
        batch.draw(tutButton, tut_x, tut_y, tut_width, tut_height);
        batch.draw(boardButton, board_x, board_y, board_width, board_height);
        batch.draw(playButton, play_x, play_y, play_width, play_height);
        batch.end();
    }

    private void savePlayers() {
        try (FileOutputStream fileOut = new FileOutputStream("saved.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            System.out.println("Save trying");
            //inspectForTextures(gameState, "gameState");

            out.writeObject(players);
            for (Player p:players){
                System.out.println(p.name);
            }
            System.out.println("Game state saved to saved.ser");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
        }
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
        backgroundTexture.dispose();
    }

    public void loadAfterDeser(){
        batch = new SpriteBatch();
        backgroundTexture = new Texture("Home_bg.png");
        exitButton = new Texture("home_exitbutton.png");
        loadButton = new Texture("home_loadbutton.png");
        boardButton = new Texture("leaderboard.png");
        playButton = new Texture("home_playbutton.png");
    }
}


