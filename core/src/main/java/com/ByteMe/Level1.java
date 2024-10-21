package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Level1 extends Level implements Screen {

    private Texture slingshot2;
    private Texture backgroundTexture;

    public Level1(MainLauncher game) {
        super(game,"slingshot1.png",100,70,50,150);
        slingshot2 = new Texture("slingshot2.png");
        backgroundTexture = new Texture("newlevel_bg.png");

        // Initialize birds
        birds = new ArrayList<>();
        Bombird b1 = new Bombird();
        b1.position.add(90);
        b1.position.add(160);
        birds.add(b1);

        TeleBird b2 = new TeleBird();
        b2.position.add(50);
        b2.position.add(70);
        birds.add(b2);

        ClassicBird b3 = new ClassicBird();
        b3.position.add(0);
        b3.position.add(70);
        birds.add(b3);


        //Initialize pigs
        pigs = new ArrayList<>();
        ClassicPig cp1 = new ClassicPig();
        cp1.position.add(615);
        cp1.position.add(70);
        pigs.add(cp1);

        ClassicPig cp2 = new ClassicPig();
        cp2.position.add(540);
        cp2.position.add(140);
        pigs.add(cp2);

        KingPig kp1 = new KingPig();
        kp1.position.add(680);
        kp1.position.add(280);
        pigs.add(kp1);

        PrettyPig pp1 = new PrettyPig();
        pp1.position.add(680);
        pp1.position.add(140);
        pigs.add(pp1);
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

            if (mouseX >= pauseButton_x && mouseX <= pauseButton_x+pauseButton_w && mouseY >= pauseButton_y && mouseY <= pauseButton_y+pauseButton_h){
                game.setScreen(new PauseGame(game,'N'));
            }

        }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
        for (Bird bird : birds) {
            batch.draw(bird.texture, bird.position.get(0), bird.position.get(1), bird.size.get(0), bird.size.get(1));
        }

        for (Pig pig : pigs) {
            batch.draw(pig.texture, pig.position.get(0), pig.position.get(1), pig.size.get(0), pig.size.get(1));
        }

        batch.draw(pauseButton, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h);

        batch.draw(slingshot2, slingshot.position.get(0) - 5, slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
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
        super.dispose(); // Dispose from the parent class if necessary
        batch.dispose();
        slingshot2.dispose();
        backgroundTexture.dispose();
        for (Bird bird : birds) {
            if (bird.texture != null) {
                bird.texture.dispose(); // Dispose each bird's texture
            }
        }
    }
}
