package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Level1 extends Level implements Screen , InputProcessor {

    private final Texture slingshot2;
    private final Texture backgroundTexture;
    private final Player player;
    private final MainLauncher game;
    private boolean isDragging = false; // Tracks if the bird is being dragged
    private Vector2 initialPosition;   // Stores the initial position of the first bird
    private ShapeRenderer shapeRenderer;
    private Vector2 initialSlingshotPosition;
    private Vector2 dragStartPosition;
    private final Texture bandTexture;
    private final Vector2 anchorBack;  // Behind the bird
    private final Vector2 anchorFront;// In front of the bird
    private boolean explosionActive = false; // To track if an explosion is active
    private float explosionDuration = 1f; // Duration for which the explosion is visible
    private float explosionTimer = 0f; // Timer to track how long the explosion has been active
    Texture explosionTexture = new Texture("blast.png");


    public Level1(MainLauncher game, Player player) {
        super(game,"slingshot1.png",100,70,50,150);
        slingshot2 = new Texture("slingshot2.png");
        backgroundTexture = new Texture("Level1_bg.png");
        this.player = player;
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        initialSlingshotPosition = new Vector2(slingshot.position.get(0), slingshot.position.get(1));
        bandTexture = new Texture("band.png");
        anchorBack = new Vector2(slingshot.position.get(0) + 20, slingshot.position.get(1) + 130);
        anchorFront = new Vector2(slingshot.position.get(0) + 55, slingshot.position.get(1) + 130);


        // Initialize birds
        birds = new ArrayList<>();
        Bombird b1 = new Bombird();
        b1.position.set(90, 160);
        birds.add(b1);

        TeleBird b2 = new TeleBird();
        b2.position.set(50, 70);
        birds.add(b2);

        ClassicBird b3 = new ClassicBird();
        b3.position.set(0, 70);
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
        Gdx.input.setInputProcessor(this);
        initialPosition = new Vector2(birds.get(0).position.x, birds.get(0).position.y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Handle mouse clicks for UI (pause, win, loss)
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

        // Draw background
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (Bird bird : birds) {
            if (bird instanceof Bombird && ((Bombird) bird).hasExploded) {
                // Draw blast texture if Bombird has exploded
                batch.draw(((Bombird) bird).blastTexture, bird.position.x, bird.position.y, bird.size.get(0), bird.size.get(1));
            } else {
                // Draw normal bird texture
                batch.draw(bird.texture, bird.position.x, bird.position.y, bird.size.get(0), bird.size.get(1));
            }
        }

        // Draw obstacles and pigs after birds
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof TNT && ((TNT) obstacle).hasExploded) {
                // Draw blast texture for TNT if it has exploded
                batch.draw(((TNT) obstacle).blastTexture, obstacle.position.x, obstacle.position.y, obstacle.width, obstacle.height);
            } else {
                obstacle.render(batch); // Render normally if not exploded
            }
        }

        // Draw elastic bands (before birds and slingshot)
        if (isDragging) {
            Bird firstBird = birds.get(0);

            // Bird's current position
            float birdX = firstBird.position.x;
            float birdY = firstBird.position.y;

            // Draw the back band
            float angleBack = MathUtils.atan2(birdY - anchorBack.y, birdX - anchorBack.x) * MathUtils.radiansToDegrees;
            float lengthBack = Vector2.dst(anchorBack.x, anchorBack.y, birdX, birdY);

            batch.draw(
                bandTexture,
                anchorBack.x, anchorBack.y, // Position
                0, 0, // Origin
                lengthBack, 10, // Width (stretch) and height (thickness)
                1, 1, // Scale
                angleBack, // Rotation angle
                0, 0,  // Texture region start
                bandTexture.getWidth(), bandTexture.getHeight(),
                false, false  // Flip X/Y
            );

            // Draw the front band
            float angleFront = MathUtils.atan2(birdY - anchorFront.y, birdX - anchorFront.x) * MathUtils.radiansToDegrees;
            float lengthFront = Vector2.dst(anchorFront.x, anchorFront.y, birdX, birdY);

            batch.draw(
                bandTexture,
                anchorFront.x, anchorFront.y,
                0, 0,
                lengthFront, 10,
                1, 1,
                angleFront,
                0, 0,
                bandTexture.getWidth(), bandTexture.getHeight(),
                false, false
            );
        }

        // Draw slingshot
        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));

        // Draw birds
        for (Bird bird : birds) {
            batch.draw(bird.texture, bird.position.x, bird.position.y, bird.size.get(0), bird.size.get(1));
        }

        // Draw obstacles and pigs after birds
        for (Obstacle obstacle : obstacles) {
            obstacle.render(batch);
        }

        // Draw pigs
        for (Pig pig : pigs) {
            batch.draw(pig.texture, pig.position.get(0), pig.position.get(1), pig.size.get(0), pig.size.get(1));
        }

        // Draw UI buttons (pause, win, loss)
        batch.draw(pauseButton, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h);
        batch.draw(winButton, winButton_x, winButton_y, winButton_w, winButton_h);
        batch.draw(lossButton, lossButton_x, lossButton_y, lossButton_w, lossButton_h);

        // Draw the secondary slingshot overlay (slingshot2)
        batch.draw(slingshot2, slingshot.position.get(0) - 5, slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));

        batch.end();

        if (isDragging) {
            Gdx.gl.glLineWidth(15);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
            shapeRenderer.setColor(Color.WHITE);

            // Initial position and other parameters
            Vector2 start = new Vector2(birds.get(0).position.x, birds.get(0).position.y);

            // Constants for trajectory
            float v0 = 620f; // Increased initial speed to go farther along x-axis
            float angle = 30f; // A smaller angle for a flatter trajectory (adjust as needed)
            float gravity = -400f; // Gravity, tweak for gameplay feel

            // Convert angle to radians for calculation
            float theta = MathUtils.degreesToRadians * angle;

            // Calculate the initial velocity components
            float v0x = v0 * MathUtils.cos(theta); // horizontal velocity component
            float v0y = v0 * MathUtils.sin(theta); // vertical velocity component

            // Calculate trajectory points
            float maxPoints = 10;
            for (int i = 1; i <= maxPoints; i++) {
                float t = i / (float) maxPoints;  // time step

                // Calculate x and y position at time t
                float x = start.x + v0x * t;
                float y = start.y + v0y * t + 0.5f * gravity * t * t; // Gravity affects y motion

                // Draw trajectory points
                shapeRenderer.point(x, y, 0);
            }

            if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                Bird firstBird = birds.get(0);
                firstBird.velocity.set(v0x, v0y); // Set the bird's velocity based on the drag
                firstBird.isFlying = true; // Start flying
                isDragging = false; // Stop dragging
            }

            shapeRenderer.end();
            Gdx.gl.glLineWidth(1);
        }

        for (Bird bird : birds) {
            if (bird.isFlying && bird.trajectoryPoints != null && !bird.trajectoryPoints.isEmpty()) {
                // Smoothly interpolate between trajectory points
                Vector2 currentPoint = bird.trajectoryPoints.get(0);
                float moveSpeed = 25f; // Adjust for smoother movement

                bird.position.x = MathUtils.lerp(bird.position.x, currentPoint.x, moveSpeed * Gdx.graphics.getDeltaTime());
                bird.position.y = MathUtils.lerp(bird.position.y, currentPoint.y, moveSpeed * Gdx.graphics.getDeltaTime());

                // Remove point when close enough
                if (Vector2.dst(bird.position.x, bird.position.y, currentPoint.x, currentPoint.y) < 10f) {
                    bird.trajectoryPoints.remove(0);
                }

                if (bird.position.y <= 60) {
                    bird.isFlying = false;
                    birds.remove(bird);
                    break;
                }

                // Stop flying if no more points
                if (bird.trajectoryPoints.isEmpty()) {
                    bird.isFlying = false;
                }
            }
        }

        // Create safe copies of collections
        List<Bird> activeBirds = new ArrayList<>(birds);
        List<Pig> activePigs = new ArrayList<>(pigs);
        List<Obstacle> activeObstacles = new ArrayList<>(obstacles);

        // Tracking lists for removal
        List<Bird> birdsToRemove = new ArrayList<>();
        List<Pig> pigsToRemove = new ArrayList<>();
        List<Obstacle> obstaclesToRemove = new ArrayList<>();

        // Process flying birds using an iterator for safe removal
        Iterator<Bird> birdIterator = activeBirds.iterator();
        while (birdIterator.hasNext()) {
            Bird bird = birdIterator.next();
            if (bird != null && bird.isFlying) {
                // Check collisions with pigs
                for (Pig pig : activePigs) {
                    handleCollision(bird, pig);
                    if (pig.isDestroyed) {
                        pigsToRemove.add(pig);
                    }
                }
                // Check collisions with obstacles
                for (Obstacle obstacle : activeObstacles) {
                    handleCollision(bird, obstacle);
                    if (obstacle.isDestroyed) {
                        obstaclesToRemove.add(obstacle);
                    }
                }

                // Mark bird for removal if not flying
                if (!bird.isFlying) {
                    birdsToRemove.add(bird);
                }
            }
            if (explosionActive) {
                batch.begin();
                batch.draw(explosionTexture, Gdx.graphics.getWidth() / 2 - explosionTexture.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - explosionTexture.getHeight() / 2);
                batch.end();

                // Update timer for how long the explosion has been active
                explosionTimer += delta;
                if (explosionTimer >= explosionDuration) {
                    explosionActive = false; // Reset after duration
                }
            }
        }

        // Safe removal of destroyed/inactive entities using iterators
        birds.removeAll(birdsToRemove);

        Iterator<Pig> pigIterator = pigs.iterator();
        while (pigIterator.hasNext()) {
            Pig pig = pigIterator.next();
            if (pigsToRemove.contains(pig)) {
                pigIterator.remove(); // Safely remove using iterator
            }
        }

        Iterator<Obstacle> obstacleIterator = obstacles.iterator();
        while (obstacleIterator.hasNext()) {
            Obstacle obstacle = obstacleIterator.next();
            if (obstaclesToRemove.contains(obstacle)) {
                obstacleIterator.remove(); // Safely remove using iterator
            }
        }

    }
    private boolean checkCollision(Bird bird, Pig pig) {
        return bird.position.x < pig.position.get(0) + pig.size.get(0) &&
            bird.position.x + bird.size.get(0) > pig.position.get(0) &&
            bird.position.y < pig.position.get(1) + pig.size.get(1) &&
            bird.position.y + bird.size.get(1) > pig.position.get(1);
    }

    private boolean checkCollision(Bird bird, Obstacle obstacle) {
        return bird.position.x < obstacle.position.x + obstacle.width &&
            bird.position.x + bird.size.get(0) > obstacle.position.x &&
            bird.position.y < obstacle.position.y + obstacle.height &&
            bird.position.y + bird.size.get(1) > obstacle.position.y;
    }

    private void handleCollision(Bird bird, Pig pig) {
        if (checkCollision(bird, pig)) {
            pig.takeDamage(bird.damage);
            bird.isFlying = false;

            if (bird instanceof Bombird) {
                Bombird bombird = (Bombird) bird;
                bombird.hasExploded = true;
                explosionActive = true;
                explosionTimer = 0f;
                for (Pig p : pigs) {
                    if (Vector2.dst(bird.position.x, bird.position.y, p.position.get(0), p.position.get(1)) <= 100) {
                        p.takeDamage(3);
                    }
                }
                for (Obstacle o : obstacles) {
                    if (Vector2.dst(bird.position.x, bird.position.y, o.position.x, o.position.y) <= 100) {
                        o.takeDamage(3);
                    }
                }
            }
        }
    }

    private void handleCollision(Bird bird, Obstacle obstacle) {
        if (checkCollision(bird, obstacle)) {
            obstacle.takeDamage(bird.damage);
            bird.isFlying = false;

            // TNT explosion logic
            if (obstacle instanceof TNT) {
                TNT tnt = (TNT) obstacle;
                tnt.hasExploded = true;
                explosionActive = true;
                explosionTimer = 0f;
                for (Pig p : pigs) {
                    if (Vector2.dst(obstacle.position.x, obstacle.position.y, p.position.get(0), p.position.get(1)) <= 100) {
                        p.takeDamage(3);
                    }
                }
                for (Obstacle o : obstacles) {
                    if (o != tnt && Vector2.dst(obstacle.position.x, obstacle.position.y, o.position.x, o.position.y) <= 100) {
                        o.takeDamage(3);
                    }
                }

            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY; // Convert to game coordinates
        Bird firstBird = birds.get(0);
        float birdX = firstBird.position.x;
        float birdY = firstBird.position.y;
        float birdWidth = firstBird.size.get(0);
        float birdHeight = firstBird.size.get(1);

        // Check if click is within the bounds of the first bird
        if (screenX >= birdX && screenX <= birdX + birdWidth && screenY >= birdY && screenY <= birdY + birdHeight) {
            isDragging = true; // Start dragging
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            screenY = Gdx.graphics.getHeight() - screenY;

            // Constrain dragging backward only
            if (screenX <= initialSlingshotPosition.x && screenY <= 160 && screenY >= initialSlingshotPosition.y + 10) {
                birds.get(0).position.set(0, screenX);
                birds.get(0).position.set(1, screenY);
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isDragging) {
            screenY = Gdx.graphics.getHeight() - screenY; // Convert to game coordinates

            Bird firstBird = birds.get(0);

            // Constants for trajectory calculation
            float v0 = 620f; // Initial velocity
            float angle = 30f; // Launch angle
            float gravity = -400f; // Gravity constant

            float theta = MathUtils.degreesToRadians * angle;

            // Calculate velocity components based on drag position
            float dragDistance = Vector2.dst(initialSlingshotPosition.x, initialSlingshotPosition.y, screenX, screenY);
            float normalizedDistance = MathUtils.clamp(dragDistance / 100f, 0f, 1f); // Normalize drag distance

            float v0x = v0 * MathUtils.cos(theta) * normalizedDistance; // Horizontal velocity
            float v0y = v0 * MathUtils.sin(theta) * normalizedDistance; // Vertical velocity

            // Store trajectory points for the bird to follow
            firstBird.trajectoryPoints = calculateTrajectoryPoints(
                firstBird.position.x,
                firstBird.position.y,
                v0x,
                v0y,
                gravity
            );

            // Set bird's velocity and flying state
            firstBird.velocity.set(v0x, v0y);
            firstBird.isFlying = true;
            isDragging = false;

            return true;
        }
        return false;
    }

    private ArrayList<Vector2> calculateTrajectoryPoints(float startX, float startY, float v0x, float v0y, float gravity) {
        ArrayList<Vector2> points = new ArrayList<>();
        float maxTime = 2f;  // Total flight time
        float maxPoints = 10;  // Match the number of points in trajectory drawing

        for (int i = 1; i <= maxPoints; i++) {
            float t = i / maxPoints * maxTime;  // Evenly distributed time points

            // Calculate x and y position at time t (exactly matching trajectory drawing logic)
            float x = startX + v0x * t;
            float y = startY + v0y * t + 0.5f * gravity * t * t;
            points.add(new Vector2(x, y));
        }

        return points;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
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
        shapeRenderer.dispose();
        bandTexture.dispose();
    }
}
