package com.ByteMe;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoginScreen implements Screen {
    private final Game game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final Texture exitButton;
    private final Texture playButton;

    public LoginScreen(MainLauncher game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundTexture = new Texture("login.png");
        exitButton = new Texture("ExitButton.png");
        playButton = new Texture("PlayButton.png");
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= 15 && mouseX <= 15+80 && mouseY >= 15 && mouseY <= 15+30) {
                Gdx.app.exit();
            }

            if (mouseX >= 325 && mouseX <= 325+150 && mouseY >= 50 && mouseY <= 50+60){
                //PLAY BUTTON FUNCTIONALITY
                //store name, error or same screen if null
                //create new player with name or check for already existing play
                game.setScreen(new HomeScreen(game));
            }

        }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(exitButton, 15, 15, 80, 30);
        batch.draw(playButton, 325, 50, 150, 60);
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
        backgroundTexture.dispose();
        exitButton.dispose();
        playButton.dispose();
    }
}
