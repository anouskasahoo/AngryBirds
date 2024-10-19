package com.ByteMe;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class LoadingScreen implements Screen {
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private MainLauncher game;

    public LoadingScreen(MainLauncher game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundTexture = new Texture("background.png");

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new LoginScreen(game));
            }
        }, 2);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

