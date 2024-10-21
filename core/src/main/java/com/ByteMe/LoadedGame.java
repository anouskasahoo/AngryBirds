package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class LoadedGame extends Level implements Screen {
    private Texture bgtexture; // Image for the background

    public LoadedGame(MainLauncher game) {
        super(game,"pulled_slingshot.png",90,70,250,150);
        slingshot2 = new Texture("pulled_slingshot1.png");
        bgtexture = new Texture("loadedgame_bg.png");

        // Initialize birds
        birds = new ArrayList<>();
        TeleBird b1 = new TeleBird();
        b1.position.add(80);
        b1.position.add(160);
        birds.add(b1);

        ClassicBird b2 = new ClassicBird();
        b2.position.add(50);
        b2.position.add(70);
        birds.add(b2);


        //Initialize pigs
        pigs = new ArrayList<>();
        ClassicPig cp1 = new ClassicPig();
        cp1.position.add(615);
        cp1.position.add(70);
        pigs.add(cp1);

        PrettyPig pp1 = new PrettyPig();
        pp1.position.add(680);
        pp1.position.add(140);
        pigs.add(pp1);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= pauseButton_x && mouseX <= pauseButton_x+pauseButton_w && mouseY >= pauseButton_y && mouseY <= pauseButton_y+pauseButton_h){
                game.setScreen(new PauseGame(game,'L'));
            }

        }

        batch.begin();
        batch.draw(bgtexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
        for (Bird bird : birds) {
            batch.draw(bird.texture, bird.position.get(0), bird.position.get(1), bird.size.get(0), bird.size.get(1));
        }

        for (Pig pig : pigs) {
            batch.draw(pig.texture, pig.position.get(0), pig.position.get(1), pig.size.get(0), pig.size.get(1));
        }

        batch.draw(pauseButton, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h);

        batch.draw(slingshot2, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
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
