/*
package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Level1 extends Level implements Screen {

    private final Texture slingshot2;
    private final Texture backgroundTexture;
    private final Player player;
    private final MainLauncher game;

    public Level1(MainLauncher game, Player player) {
        super(game,"slingshot1.png",100,70,50,150);
        slingshot2 = new Texture("slingshot2.png");
        backgroundTexture = new Texture("Level1_bg.png");
        this.player = player;
        this.game = game;

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
        cp1.position.add(677);
        cp1.position.add(70);
        pigs.add(cp1);

        ClassicPig cp2 = new ClassicPig();
        cp2.position.add(628);
        cp2.position.add(115);
        pigs.add(cp2);

        KingPig kp1 = new KingPig();
        kp1.position.add(720);
        kp1.position.add(205);
        pigs.add(kp1);

        PrettyPig pp1 = new PrettyPig();
        pp1.position.add(720);
        pp1.position.add(110);
        pigs.add(pp1);

        obstacles.add(new Wood(new Vector2(585, 110), Wood.Orientation.HORIZONTAL));
        obstacles.add(new Wood(new Vector2(580, 70), Wood.Orientation.VERTICAL));
        obstacles.add(new TNT(new Vector2(720, 70)));
        obstacles.add(new TNT(new Vector2(630, 70)));
        obstacles.add(new TNT(new Vector2(580, 115)));
        obstacles.add(new TNT(new Vector2(720, 160)));
        obstacles.add(new Wood(new Vector2(625, 155), Wood.Orientation.HORIZONTAL));
        obstacles.add(new Wood(new Vector2(670, 115), Wood.Orientation.VERTICAL));
        obstacles.add(new Stone(new Vector2(672, 115), Stone.Orientation.HORIZONTAL));
        obstacles.add(new Stone(new Vector2(720, 115), Stone.Orientation.VERTICAL));
        obstacles.add(new Stone(new Vector2(760, 115), Stone.Orientation.VERTICAL));
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
                game.setScreen(new PauseGame(game,1, player));
            }
            if (mouseX >= winButton_x && mouseX <= winButton_x+winButton_w && mouseY >= winButton_y && mouseY <= winButton_y+winButton_h){
                game.setScreen(new Win(game, player));
            }
            if (mouseX >= lossButton_x && mouseX <= lossButton_x+lossButton_w && mouseY >= lossButton_y && mouseY <= lossButton_y+lossButton_h){
                game.setScreen(new Loss(game,1, player));
            }

        }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        batch.draw(winButton, winButton_x, winButton_y, winButton_w, winButton_h);
        batch.draw(lossButton, lossButton_x, lossButton_y, lossButton_w, lossButton_h);
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
        super.dispose();
        batch.dispose();
        slingshot2.dispose();
        backgroundTexture.dispose();
        for (Bird bird : birds) {
            if (bird.texture != null) {
                bird.texture.dispose();
            }
        }
    }
}
*/

/*
public class Level1 extends Level implements Screen {

    private final Texture slingshot2;
    private final Texture backgroundTexture;
    private final Player player;
    private final MainLauncher game;
    private Vector2 initialBirdPosition;
    private boolean isDragging = false;

    public Level1(MainLauncher game, Player player) {
        super(game, "slingshot1.png", 100, 70, 50, 150);
        slingshot2 = new Texture("slingshot2.png");
        backgroundTexture = new Texture("Level1_bg.png");
        this.player = player;
        this.game = game;

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

        // Initialize pigs
        pigs = new ArrayList<>();
        ClassicPig cp1 = new ClassicPig();
        cp1.position.add(677);
        cp1.position.add(70);
        pigs.add(cp1);

        ClassicPig cp2 = new ClassicPig();
        cp2.position.add(628);
        cp2.position.add(115);
        pigs.add(cp2);

        KingPig kp1 = new KingPig();
        kp1.position.add(720);
        kp1.position.add(205);
        pigs.add(kp1);

        PrettyPig pp1 = new PrettyPig();
        pp1.position.add(720);
        pp1.position.add(110);
        pigs.add(pp1);

        obstacles.add(new Wood(new Vector2(585, 110), Wood.Orientation.HORIZONTAL));
        obstacles.add(new Wood(new Vector2(580, 70), Wood.Orientation.VERTICAL));
        obstacles.add(new TNT(new Vector2(720, 70)));
        obstacles.add(new TNT(new Vector2(630, 70)));
        obstacles.add(new TNT(new Vector2(580, 115)));
        obstacles.add(new TNT(new Vector2(720, 160)));
        obstacles.add(new Wood(new Vector2(625, 155), Wood.Orientation.HORIZONTAL));
        obstacles.add(new Wood(new Vector2(670, 115), Wood.Orientation.VERTICAL));
        obstacles.add(new Stone(new Vector2(672, 115), Stone.Orientation.HORIZONTAL));
        obstacles.add(new Stone(new Vector2(720, 115), Stone.Orientation.VERTICAL));
        obstacles.add(new Stone(new Vector2(760, 115), Stone.Orientation.VERTICAL));

        // Set initial position for bird1 (the one on the slingshot)
        initialBirdPosition = new Vector2(90, 160);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Handle mouse click (pause, win, loss buttons)
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= pauseButton_x && mouseX <= pauseButton_x + pauseButton_w &&
                mouseY >= pauseButton_y && mouseY <= pauseButton_y + pauseButton_h) {
                game.setScreen(new PauseGame(game, 1, player));
            }
            if (mouseX >= winButton_x && mouseX <= winButton_x + winButton_w &&
                mouseY >= winButton_y && mouseY <= winButton_y + winButton_h) {
                game.setScreen(new Win(game, player));
            }
            if (mouseX >= lossButton_x && mouseX <= lossButton_x + lossButton_w &&
                mouseY >= lossButton_y && mouseY <= lossButton_y + lossButton_h) {
                game.setScreen(new Loss(game, 1, player));
            }
        }

        // Dragging the first bird
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // If the player is clicking on bird1 (the one sitting on the slingshot)
            if (!isDragging && mouseX >= birds.get(0).position.get(0) - 20 && mouseX <= birds.get(0).position.get(0) + 20 &&
                mouseY >= birds.get(0).position.get(1) - 20 && mouseY <= birds.get(0).position.get(1) + 20) {
                isDragging = true;
            }

            if (isDragging) {
                // Update bird1's position as the player drags the mouse
                birds.get(0).position.set((int) mouseX, (int) mouseY);
            }
        }

        // Launching the bird when mouse is released
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && isDragging) {
            isDragging = false;
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Calculate the force for launching the bird (based on drag distance)
            float launchForceX = (initialBirdPosition.x - mouseX) * 5; // Adjust the multiplier for power
            float launchForceY = (initialBirdPosition.y - mouseY) * 5;

            // Apply the force to the bird (this is just a placeholder - apply your physics here)
            Bird bird1 = birds.get(0);
            bird1.velocity.set(launchForceX, launchForceY);

            // Reset the bird's position to simulate being launched
            bird1.position.set((int) initialBirdPosition.x, (int) initialBirdPosition.y);
        }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));

        // Draw birds
        for (Bird bird : birds) {
            batch.draw(bird.texture, bird.position.get(0), bird.position.get(1), bird.size.get(0), bird.size.get(1));
        }

        // Draw obstacles and pigs
        for (Obstacle obstacle : obstacles) {
            obstacle.render(batch);
        }
        for (Pig pig : pigs) {
            batch.draw(pig.texture, pig.position.get(0), pig.position.get(1), pig.size.get(0), pig.size.get(1));
        }

        // Draw buttons
        batch.draw(pauseButton, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h);
        batch.draw(winButton, winButton_x, winButton_y, winButton_w, winButton_h);
        batch.draw(lossButton, lossButton_x, lossButton_y, lossButton_w, lossButton_h);
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
        super.dispose();
        batch.dispose();
        slingshot2.dispose();
        backgroundTexture.dispose();
        for (Bird bird : birds) {
            if (bird.texture != null) {
                bird.texture.dispose();
            }
        }
    }
}
*/

package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Level1 extends Level implements Screen {

    private final Texture slingshot2;
    private final Texture backgroundTexture;
    private final MainLauncher game;
    private Bird bird1;
    private Vector2 initialBirdPosition;
    private boolean isDragging = false;

    public Level1(MainLauncher game, Player player) {
        super(game, "slingshot1.png", 100, 70, 50, 150);
        slingshot2 = new Texture("slingshot2.png");
        backgroundTexture = new Texture("Level1_bg.png");
        this.game = game;

        // Initialize birds
        bird1 = new Bombird(); // Assuming Bird class has a constructor that sets the texture
        bird1.position = new ArrayList<>();
        bird1.position.add(90); // X position
        bird1.position.add(160); // Y position

        initialBirdPosition = new Vector2(bird1.position.get(0), bird1.position.get(1));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));

        // Draw the bird
        batch.draw(bird1.texture, bird1.position.get(0), bird1.position.get(1), bird1.size.get(0), bird1.size.get(1));

        batch.draw(slingshot2, slingshot.position.get(0) - 5, slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
        batch.end();
    }

    private void handleInput() {
        // Start dragging the bird on mouse press
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (isMouseInsideSlingshot(mouseX, mouseY)) {
                isDragging = true;
            }
        }

        // Drag the bird with the mouse
        if (isDragging) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            bird1.position.set((int) mouseX, (int) mouseY);
        }

        // Release the bird and launch it
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && isDragging) {
            isDragging = false;
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Calculate the force for launching the bird (based on drag distance)
            float launchForceX = (initialBirdPosition.x - mouseX) * 5; // Adjust the multiplier for power
            float launchForceY = (initialBirdPosition.y - mouseY) * 5;

            // Apply the force to the bird (this is just a placeholder - apply your physics here)
            bird1.velocity.set(launchForceX, launchForceY);

            // Reset the bird's position to simulate being launched
            bird1.position.set((int) initialBirdPosition.x, (int) initialBirdPosition.y);
        }
    }

    private boolean isMouseInsideSlingshot(float mouseX, float mouseY) {
        return mouseX >= slingshot.position.get(0) && mouseX <= slingshot.position.get(0) + slingshot.size.get(0)
            && mouseY >= slingshot.position.get(1) && mouseY <= slingshot.position.get(1) + slingshot.size.get(1);
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
        super.dispose();
        batch.dispose();
        slingshot2.dispose();
        backgroundTexture.dispose();
        bird1.texture.dispose();
    }
}
