package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sun.tools.javac.Main;

import java.util.ArrayList;

public class LoadedGame extends Level implements Screen {
    private Texture bgtexture;// Image for the background
    private final Player player;
    private final MainLauncher game;

    public LoadedGame(MainLauncher game, Player player) {
        super(game,"pulled_slingshot.png",90,70,250,150);
        slingshot2 = new Texture("pulled_slingshot1.png");
        bgtexture = new Texture("loadedgame_bg.png");
        this.game = game;
        this.player = player;

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
        cp1.position.add(677);
        cp1.position.add(70);
        pigs.add(cp1);

        /*
        ClassicPig cp2 = new ClassicPig();
        cp2.position.add(628);
        cp2.position.add(115);
        pigs.add(cp2);
         */

        PrettyPig pp1 = new PrettyPig();
        pp1.position.add(720);
        pp1.position.add(110);
        pigs.add(pp1);

        obstacles.add(new Wood(new Vector2(585, 110), Wood.Orientation.HORIZONTAL));
        obstacles.add(new Wood(new Vector2(580, 70), Wood.Orientation.VERTICAL));
        obstacles.add(new TNT(new Vector2(720, 70)));
        obstacles.add(new TNT(new Vector2(630, 70)));
        obstacles.add(new TNT(new Vector2(580, 115)));
        //obstacles.add(new TNT(new Vector2(720, 160)));
        obstacles.add(new Wood(new Vector2(625, 115), Wood.Orientation.DIAGONAL));
        //obstacles.add(new Wood(new Vector2(625, 155), Wood.Orientation.HORIZONTAL));
        //obstacles.add(new Wood(new Vector2(670, 115), Wood.Orientation.VERTICAL));
        obstacles.add(new Stone(new Vector2(672, 115), Stone.Orientation.HORIZONTAL));
        //obstacles.add(new Stone(new Vector2(720, 115), Wood.Orientation.VERTICAL));
        //obstacles.add(new Stone(new Vector2(760, 115), Wood.Orientation.VERTICAL));
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
                game.setScreen(new PauseGame(game,0, player));
            }

        }

        batch.begin();
        batch.draw(bgtexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
        for (Bird bird : birds) {
            batch.draw(bird.texture, bird.position.get(0), bird.position.get(1), bird.size.get(0), bird.size.get(1));
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.render(batch);
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
