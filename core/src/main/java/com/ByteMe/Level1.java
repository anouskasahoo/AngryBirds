/*
package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.*;


public class Level1 extends Level implements Screen , InputProcessor {

    private final Texture slingshot2;
    private final Texture backgroundTexture;
    private final Player player;
    private final MainLauncher game;
    private Vector2 initialPosition;   // Stores the initial position of the first bird
    private ShapeRenderer shapeRenderer;
    private Vector2 dragStartPosition;
    private Vector2 initialSlingshotPosition;
    private Set<Vector2> occupiedPositions;


    public Level1(MainLauncher game, Player player) {
        super(game, "slingshot1.png", 100, 70, 50, 150, player);
        slingshot2 = new Texture("slingshot2.png");
        backgroundTexture = new Texture("Level1_bg.png");
        this.player = player;
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        this.initialSlingshotPosition = new Vector2(slingshot.position.get(0), slingshot.position.get(1));


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
        cp1.position.set(677, 70);
        pigs.add(cp1);

        ClassicPig cp2 = new ClassicPig();
        cp2.position.set(628, 115);
        pigs.add(cp2);

        KingPig kp1 = new KingPig();
        kp1.position.set(720, 205);
        pigs.add(kp1);

        PrettyPig pp1 = new PrettyPig();
        pp1.position.set(720, 110);
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
        clearScreen();
        batch.begin();
        drawGameElements();
        batch.end();
        initializeOccupiedPositions();
        handleInput();
        updateBirdTrajectory();
        updateFlyingBirds(delta);
        handleCollisions();
        checkGameConditions();
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
        if (isDragging){
            updateFlyingBirds();
            handleBirdRelease();
        }
        return true;
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

    private boolean checkCollision(Bird bird, Pig pig) {
        return bird.position.x < pig.position.x + pig.size.get(0) &&
            bird.position.x + bird.size.get(0) > pig.position.x &&
            bird.position.y < pig.position.y + pig.size.get(1) &&
            bird.position.y + bird.size.get(1) > pig.position.y;
    }

    private boolean checkCollision(Bird bird, Obstacle obstacle) {
        return bird.position.x < obstacle.position.x + obstacle.width &&
            bird.position.x + bird.size.get(0) > obstacle.position.x &&
            bird.position.y < obstacle.position.y + obstacle.height &&
            bird.position.y + bird.size.get(1) > obstacle.position.y;
    }

    private void applyGravityDamage(float destroyedX, float destroyedY) {
        for (Pig pig : pigs) {
            if (isAbove(pig.position.x, pig.position.y, destroyedX, destroyedY)) {
                pig.takeDamage(1);
                pig.position.set(destroyedX, destroyedY);
                applyGravityEffect(pig.position, 70, occupiedPositions);
            }
        }

        for (Obstacle obstacle : obstacles) {
            if (isAbove(obstacle.position.x, obstacle.position.y, destroyedX, destroyedY)) {
                obstacle.takeDamage(1);
                obstacle.position.set(destroyedX, destroyedY);
            }
        }
    }
    private void applyGravityEffect(Vector2 position, float groundY, Set<Vector2> occupiedPositions) {
        float step = 10f; // Distance to fall per step
        Vector2 newPosition = new Vector2(position);

        while (newPosition.y > groundY && !occupiedPositions.contains(new Vector2(newPosition.x, newPosition.y - step))) {
            newPosition.y -= step; // Simulate falling
        }

        occupiedPositions.remove(position); // Remove old position
        position.set(newPosition); // Update to new position
        occupiedPositions.add(newPosition); // Mark new position as occupied
    }


    private boolean isAbove(float x1, float y1, float x2, float y2) {
        float xTolerance = 100f;
        return Math.abs(x1 - x2) <= xTolerance && y1 > y2;
    }

    private void handleCollision(Bird bird, Obstacle obstacle) {
        if (checkCollision(bird, obstacle)) {
            obstacle.takeDamage(bird.damage);
            bird.isFlying = false;

            if (obstacle instanceof TNT) {
                TNT tnt = (TNT) obstacle;
                tnt.hasExploded = true;
                explosionActive = true;
                explosionTimer = 0f;

                float tntCenterX = (obstacle.position.x + obstacle.width) / 2;
                float tntCenterY = (obstacle.position.y + obstacle.height) / 2;
                float explosionRadius = 100f;

                for (Pig pig : pigs) {
                    float pigCenterX = (pig.position.x + pig.size.get(0)) / 2;
                    float pigCenterY = (pig.position.x + pig.size.get(1)) / 2;

                    float distance = Vector2.dst(tntCenterX, tntCenterY, pigCenterX, pigCenterY);

                    if (distance <= explosionRadius) {
                        pig.takeDamage(3);
                    }
                    if (pig.isDestroyed){
                        applyGravityDamage(pig.position.x, pig.position.y);
                    }
                }

                for (Obstacle otherObstacle : obstacles) {
                    if (otherObstacle != obstacle) {
                        float obstacleCenterX = (otherObstacle.position.x + otherObstacle.width) / 2;
                        float obstacleCenterY = (otherObstacle.position.y + otherObstacle.height) / 2;

                        float distance = Vector2.dst(tntCenterX, tntCenterY, obstacleCenterX, obstacleCenterY);
                        if (distance <= explosionRadius) {
                            otherObstacle.takeDamage(3);
                        }
                        if (otherObstacle.isDestroyed){
                            applyGravityDamage(otherObstacle.position.x, otherObstacle.position.y);
                        }
                    }
                }

                if (obstacle.isDestroyed) {
                    applyGravityDamage(obstacle.position.x, obstacle.position.y);
                }
            }
        }
    }

    private void initializeOccupiedPositions() {
        occupiedPositions = new HashSet<>();

        // Add initial positions of all pigs
        for (Pig pig : pigs) {
            occupiedPositions.add(new Vector2(pig.position));
        }

        // Add initial positions of all obstacles
        for (Obstacle obstacle : obstacles) {
            occupiedPositions.add(new Vector2(obstacle.position));
        }
    }

    // Helper method to ensure unique positions after movements or interactions
    private Vector2 findUniquePosition(Vector2 currentPos, Set<Vector2> occupiedPositions, float step) {
        Vector2 newPos = new Vector2(currentPos);
        while (occupiedPositions.contains(newPos)) {
            newPos.add(step, step);// Increment position systematically
            if (newPos.x >= 720){
                int randomNum = 600 + (int)(Math.random() * 151);
                newPos.x = randomNum;
            }
        }
        occupiedPositions.add(newPos);
        return newPos;
    }

    // Modified methods
    private void handleCollision(Bird bird, Pig pig) {
        if (checkCollision(bird, pig)) {
            pig.takeDamage(bird.damage);
            bird.isFlying = false;

            if (bird instanceof Bombird) {
                Bombird bombird = (Bombird) bird;
                bombird.hasExploded = true;
                explosionActive = true;
                explosionTimer = 0f;

                float birdCenterX = (bird.position.x + bird.size.get(0)) / 2;
                float birdCenterY = (bird.position.y + bird.size.get(1)) / 2;
                float explosionRadius = 80f;

                for (Pig otherPig : pigs) {
                    if (otherPig == pig) continue; // Skip the original pig
                    float pigCenterX = (otherPig.position.x + otherPig.size.get(0)) / 2;
                    float pigCenterY = (otherPig.position.y + otherPig.size.get(1)) / 2;

                    float distance = Vector2.dst(birdCenterX, birdCenterY, pigCenterX, pigCenterY);
                    if (distance <= explosionRadius) {
                        otherPig.takeDamage(3);
                    }
                    if (otherPig.isDestroyed) {
                        occupiedPositions.remove(otherPig.position); // Remove old position
                        otherPig.position = findUniquePosition(otherPig.position, occupiedPositions, 10f);
                        applyGravityDamage(otherPig.position.x, otherPig.position.y);
                    }
                }

                for (Obstacle obstacle : obstacles) {
                    float obstacleCenterX = (obstacle.position.x + obstacle.width) / 2;
                    float obstacleCenterY = (obstacle.position.y + obstacle.height) / 2;

                    float distance = Vector2.dst(birdCenterX, birdCenterY, obstacleCenterX, obstacleCenterY);
                    if (distance <= explosionRadius) {
                        obstacle.takeDamage(3);
                    }
                    if (obstacle.isDestroyed) {
                        occupiedPositions.remove(obstacle.position); // Remove old position
                        obstacle.position = findUniquePosition(obstacle.position, occupiedPositions, 10f);
                        applyGravityDamage(obstacle.position.x, obstacle.position.y);
                    }

                    if (obstacle instanceof TNT) {
                        TNT tnt = (TNT) obstacle;
                        tnt.hasExploded = true;
                        handleTNTExplosion(tnt);
                    }
                }
            }
        }

        if (pig.isDestroyed) {
            occupiedPositions.remove(pig.position); // Remove old position
            pig.position = findUniquePosition(pig.position, occupiedPositions, 10f);
            applyGravityDamage(pig.position.x, pig.position.y);
        }
    }

    private void handleTNTExplosion(TNT tnt) {
        float tntCenterX = tnt.position.x + tnt.width / 2;
        float tntCenterY = tnt.position.y + tnt.height / 2;
        float explosionRadius = 100f;

        for (Pig pig : pigs) {
            float pigCenterX = (pig.position.x + pig.size.get(0)) / 2;
            float pigCenterY = (pig.position.y + pig.size.get(1)) / 2;

            float distance = Vector2.dst(tntCenterX, tntCenterY, pigCenterX, pigCenterY);
            if (distance <= explosionRadius) {
                pig.takeDamage(3);
            }
            if (pig.isDestroyed) {
                occupiedPositions.remove(pig.position); // Remove old position
                pig.position = findUniquePosition(pig.position, occupiedPositions, 10f);
                applyGravityDamage(pig.position.x, pig.position.y);
            }
        }

        for (Obstacle obstacle : obstacles) {
            if (obstacle != tnt) {
                float obstacleCenterX = (obstacle.position.x + obstacle.width) / 2;
                float obstacleCenterY = (obstacle.position.y + obstacle.height) / 2;

                float distance = Vector2.dst(tntCenterX, tntCenterY, obstacleCenterX, obstacleCenterY);
                if (distance <= explosionRadius) {
                    obstacle.takeDamage(3);
                }
                if (obstacle.isDestroyed) {
                    occupiedPositions.remove(obstacle.position); // Remove old position
                    obstacle.position = findUniquePosition(obstacle.position, occupiedPositions, 10f);
                    applyGravityDamage(obstacle.position.x, obstacle.position.y);
                }

                if (obstacle instanceof TNT && !((TNT) obstacle).hasExploded) {
                    TNT chainTnt = (TNT) obstacle;
                    chainTnt.hasExploded = true;
                    handleTNTExplosion(chainTnt);
                }
            }
        }
    }


    private void clearScreen() {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawGameElements() {
        // Draw background
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw slingshot bands if dragging
        if (isDragging) {
            drawSlingshotBands();
        }

        // Draw game objects
        drawSlingshot();
        drawObstacles();
        drawPigs();
        drawUI();
    }

    private void drawSlingshotBands() {
        Bird firstBird = birds.get(0);
        float birdX = firstBird.position.x;
        float birdY = firstBird.position.y;

        // Back band
        float angleBack = MathUtils.atan2(birdY - anchorBack.y, birdX - anchorBack.x) * MathUtils.radiansToDegrees;
        float lengthBack = Vector2.dst(anchorBack.x, anchorBack.y, birdX, birdY);
        batch.draw(bandTexture, anchorBack.x, anchorBack.y, 0, 0, lengthBack, 10, 1, 1, angleBack,
            0, 0, bandTexture.getWidth(), bandTexture.getHeight(), false, false);

        // Front band
        float angleFront = MathUtils.atan2(birdY - anchorFront.y, birdX - anchorFront.x) * MathUtils.radiansToDegrees;
        float lengthFront = Vector2.dst(anchorFront.x, anchorFront.y, birdX, birdY);
        batch.draw(bandTexture, anchorFront.x, anchorFront.y, 0, 0, lengthFront, 10, 1, 1, angleFront,
            0, 0, bandTexture.getWidth(), bandTexture.getHeight(), false, false);
    }

    private void drawSlingshot() {
        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1),
            slingshot.size.get(0), slingshot.size.get(1));
        drawBirds();
        batch.draw(slingshot2, slingshot.position.get(0) - 5, slingshot.position.get(1),
            slingshot.size.get(0), slingshot.size.get(1));
    }

    private void drawBirds() {
        for (Bird bird : birds) {
            batch.draw(bird.texture, bird.position.x, bird.position.y,
                bird.size.get(0), bird.size.get(1));
        }
    }

    private void drawObstacles() {
        for (Obstacle obstacle : obstacles) {
            obstacle.render(batch);
        }
    }

    private void drawPigs() {
        for (Pig pig : pigs) {
            batch.draw(pig.texture, pig.position.x, pig.position.y,
                pig.size.get(0), pig.size.get(1));
        }
    }

    private void drawUI() {
        batch.draw(pauseButton, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h);
        batch.draw(winButton, winButton_x, winButton_y, winButton_w, winButton_h);
        batch.draw(lossButton, lossButton_x, lossButton_y, lossButton_w, lossButton_h);
    }

    private void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            handleButtonClicks();
        }
    }

    private void handleButtonClicks() {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (isClickInBounds(mouseX, mouseY, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h)) {
            game.setScreen(new PauseGame(game, 1, player));
        }
        if (isClickInBounds(mouseX, mouseY, winButton_x, winButton_y, winButton_w, winButton_h)) {
            game.setScreen(new Win(game, player));
        }
        if (isClickInBounds(mouseX, mouseY, lossButton_x, lossButton_y, lossButton_w, lossButton_h)) {
            game.setScreen(new Loss(game, 1, player));
        }
    }

    private boolean isClickInBounds(float x, float y, float buttonX, float buttonY, float width, float height) {
        return x >= buttonX && x <= buttonX + width && y >= buttonY && y <= buttonY + height;
    }


    private void updateFlyingBirds() {
        Iterator<Bird> iterator = birds.iterator();
        while (iterator.hasNext()) {
            Bird bird = iterator.next();
            if (bird.isFlying && bird.trajectoryPoints != null && !bird.trajectoryPoints.isEmpty()) {
                Vector2 targetPoint = bird.trajectoryPoints.get(0);
                updateBirdPosition(bird, targetPoint);

                if (bird.position.y <= 60) {
                    handleBirdLanding(bird, iterator);
                    break;
                }

                if (bird.trajectoryPoints.isEmpty()) {
                    bird.isFlying = false;
                }
            }
        }
    }

    private void updateBirdPosition(Bird bird, Vector2 targetPoint) {
        float moveSpeed = 0.2f;
        bird.position.x = MathUtils.lerp(bird.position.x, targetPoint.x, moveSpeed);
        bird.position.y = MathUtils.lerp(bird.position.y, targetPoint.y, moveSpeed);

        if (Vector2.dst(bird.position.x, bird.position.y, targetPoint.x, targetPoint.y) < 5f) {
            bird.trajectoryPoints.remove(0);
        }
    }

    private void handleBirdLanding(Bird bird, Iterator<Bird> iterator) {
        bird.isFlying = false;
        iterator.remove();

        if (!birds.isEmpty()) {
            Bird nextBird = birds.get(0);
            nextBird.position.set(90, 160);
            nextBird.isFlying = false;
            nextBird.velocity.setZero();
        }
    }

    private void handleCollisions() {
        // Create safe copies of collections
        List<Bird> activeBirds = new ArrayList<>(birds);
        List<Pig> activePigs = new ArrayList<>(pigs);
        List<Obstacle> activeObstacles = new ArrayList<>(obstacles);

        // Tracking lists for removal
        List<Bird> birdsToRemove = new ArrayList<>();
        List<Pig> pigsToRemove = new ArrayList<>();
        List<Obstacle> obstaclesToRemove = new ArrayList<>();

        // Process flying birds using an iterator for safe removal
        for (Bird bird : activeBirds) {
            if (bird != null && bird.isFlying) {
                // Check collisions with pigs
                for (Pig pig : activePigs) {
                    handleCollision(bird, pig);
                    if (bird instanceof Bombird && ((Bombird) bird).hasExploded) {
                        drawExplosion(bird);
                    }
                    if (pig.isDestroyed) {
                        pigsToRemove.add(pig);
                        occupiedPositions.remove(pig.position); // Remove old position
                    }
                }

                // Check collisions with obstacles
                for (Obstacle obstacle : activeObstacles) {
                    handleCollision(bird, obstacle);
                    if (obstacle instanceof TNT && ((TNT) obstacle).hasExploded) {
                        drawTNTExplosion(obstacle);
                    }
                    if (obstacle.isDestroyed || (obstacle instanceof TNT && ((TNT) obstacle).hasExploded)) {
                        obstaclesToRemove.add(obstacle);
                    }
                }

                if (!bird.isFlying) {
                    birdsToRemove.add(bird);
                }
            }
        }

        performCollisionCleanup(birdsToRemove, pigsToRemove, obstaclesToRemove);
    }

    private void drawExplosion(Bird bird) {
        batch.begin();
        batch.draw(((Bombird) bird).blastTexture, bird.position.x, bird.position.y);
        batch.end();
    }

    private void drawTNTExplosion(Obstacle obstacle) {
        batch.begin();
        batch.draw(((TNT) obstacle).blastTexture, obstacle.position.x, obstacle.position.y);
        batch.end();
    }

    private void performCollisionCleanup(List<Bird> birdsToRemove, List<Pig> pigsToRemove, List<Obstacle> obstaclesToRemove) {
        // Remove birds and reset next bird if needed
        birds.removeAll(birdsToRemove);
        if (!birds.isEmpty() && !birdsToRemove.isEmpty()) {
            resetNextBird();
        }

        // Remove destroyed pigs and obstacles
        pigs.removeAll(pigsToRemove);
        obstacles.removeAll(obstaclesToRemove);
    }

    private void resetNextBird() {
        Bird nextBird = birds.get(0);
        nextBird.position.set(90, 160);
        nextBird.isFlying = false;
        nextBird.velocity.x = 0;
        nextBird.velocity.y = 0;
    }

    private void checkGameConditions() {
        if (pigs.isEmpty()) {
            game.setScreen(new Win(game, player));
            return;
        }

        if (birds.isEmpty()) {
            // Make sure no birds are currently in flight
            boolean birdInFlight = false;
            for (Bird bird : birds) {
                if (bird.isFlying) {
                    birdInFlight = true;
                    break;
                }
            }

            // Only transition to lose screen if no birds are in flight
            if (!birdInFlight) {
                game.setScreen(new Loss(game, 1, player));
                return;
            }
        }
    }

    private void handleBirdRelease() {
        if (!birds.isEmpty()) {
            Bird bird = birds.get(0);
            bird.launch();
        }
        isDragging = false;
    }

    private void updateBirdPhysics(Bird bird, float delta) {
        // Update position based on velocity
        bird.position.x += bird.velocity.x * delta;
        bird.position.y += bird.velocity.y * delta;

        // Apply gravity to vertical velocity
        bird.velocity.y += GRAVITY * delta;
    }

    private void updateFlyingBirds(float delta) {
        Iterator<Bird> iterator = birds.iterator();
        while (iterator.hasNext()) {
            Bird bird = iterator.next();
            if (bird.isFlying) {
                updateBirdPhysics(bird, delta);

                // Check if bird has hit the ground
                if (bird.position.y <= 60) {
                    handleBirdLanding(bird, iterator);
                    break;
                }
            }
        }
    }

    private ArrayList<Vector2> calculateTrajectoryPoints(float startX, float startY, float velocityX, float velocityY, float gravity) {
        ArrayList<Vector2> points = new ArrayList<>();
        float timestep = 1/60f; // Simulation timestep (60 FPS)
        float maxTime = 5.0f;   // Maximum time to simulate

        float posX = startX;
        float posY = startY;
        float velX = velocityX;
        float velY = velocityY;

        for (float t = 0; t < maxTime; t += timestep) {
            // Add current position to trajectory
            points.add(new Vector2(posX, posY));

            // Update position and velocity using physics
            posX += velX * timestep;
            posY += velY * timestep;
            velY += gravity * timestep;

            // Stop if trajectory hits ground
            if (posY <= 60) { // Adjust ground height as needed
                break;
            }
        }

        return points;
    }

    private void updateBirdTrajectory() {
        if (!isDragging || birds.isEmpty()) return;

        Bird firstBird = birds.get(0);
        int screenY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // Calculate launch velocity based on drag
        float dragDX = initialSlingshotPosition.x - Gdx.input.getX();
        float dragDY = initialSlingshotPosition.y - screenY;

        float dragDistance = Math.min(Vector2.len(dragDX, dragDY), MAX_DRAG_DISTANCE);
        float dragAngle = MathUtils.atan2(dragDY, dragDX);

        // Calculate launch velocity
        float launchSpeed = LAUNCH_SPEED_MULTIPLIER * (dragDistance / MAX_DRAG_DISTANCE);
        float launchVelocityX = launchSpeed * MathUtils.cos(dragAngle);
        float launchVelocityY = launchSpeed * MathUtils.sin(dragAngle);

        // Store launch velocity in bird for later use
        firstBird.launchVelocityX = launchVelocityX;
        firstBird.launchVelocityY = launchVelocityY;

        // Draw trajectory preview
        drawTrajectoryPreview(firstBird, launchVelocityX, launchVelocityY);
    }

    private void drawTrajectoryPreview(Bird bird, float velocityX, float velocityY) {
        // Get trajectory points
        ArrayList<Vector2> previewPoints = calculateTrajectoryPoints(
            bird.position.x, bird.position.y, velocityX, velocityY, GRAVITY);

        // Begin shape rendering
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Draw dotted line trajectory
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Use white dots with fade out
        int totalPoints = previewPoints.size();
        float dotSpacing = 5; // Show a dot every X points
        float dotSize = 3f;   // Size of trajectory dots

        for (int i = 0; i < totalPoints; i += dotSpacing) {
            Vector2 point = previewPoints.get(i);

            // Calculate fade out based on position in trajectory
            float alpha = 1.0f - ((float)i / totalPoints);
            shapeRenderer.setColor(1, 1, 1, alpha * 0.7f);

            // Draw circular dot
            shapeRenderer.circle(point.x, point.y, dotSize * (1.0f - ((float)i / totalPoints)));
        }

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
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
