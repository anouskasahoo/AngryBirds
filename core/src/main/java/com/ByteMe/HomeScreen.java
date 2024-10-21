package com.ByteMe;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HomeScreen implements Screen {
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private MainLauncher game;
    private final Texture exitButton;
    private final Texture loadButton;
    private final Texture boardButton;
    private final Texture playButton;

    public HomeScreen(MainLauncher game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundTexture = new Texture("Home_bg.png");
        exitButton = new Texture("home_exitbutton.png");
        loadButton = new Texture("home_loadbutton.png");
        boardButton = new Texture("leaderboard.png");
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
        int play_x = 250;
        int play_y = 75;
        int play_width = 330;
        int play_height = 200;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= exit_x && mouseX <= exit_x+exit_width && mouseY >= exit_y && mouseY <= exit_y+exit_height) {
                Gdx.app.exit();
            }
            if (mouseX >= load_x && mouseX <= load_y+load_width && mouseY >= load_y && mouseY <= load_y+load_height) {
                game.setScreen(new LoadedGame(game));
            }
            if (mouseX >= board_x && mouseX <= board_x+board_width && mouseY >= board_y && mouseY <= board_y+board_height) {
                game.setScreen(new Leaderboard(game));
            }
            if (mouseX >= play_x && mouseX <= play_x+play_width && mouseY >= play_y && mouseY <= play_y+play_height) {
                game.setScreen(new Hand(game,1));
            }

        }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(exitButton, exit_x, exit_y, exit_width, exit_height);
        batch.draw(loadButton, load_x, load_y, load_width, load_height);
        batch.draw(boardButton, board_x, board_y, board_width, board_height);
        batch.draw(playButton, play_x, play_y, play_width, play_height);
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


