package com.ByteMe;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HomeScreen implements Screen {
    private SpriteBatch batch;
    private Texture backgroundTexture; // Image for the background

    public HomeScreen() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("background.jpg"); // Load bg image
    }

    @Override
    public void show() {
        // This method is called when the screen becomes active
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1); // Light Blue background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Handle window resizing if needed
    }

    @Override
    public void pause() {
        // Handle pause state if needed
    }

    @Override
    public void resume() {
        // Handle resume state if needed
    }

    @Override
    public void hide() {
        // Called when the screen is no longer active
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose(); // Clean up resources
    }
}

