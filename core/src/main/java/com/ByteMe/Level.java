package com.ByteMe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Level {
    protected final MainLauncher game;
    protected final SpriteBatch batch;
    protected final Texture pauseButton;
    protected final Texture winButton;
    protected final Texture lossButton;
    protected final Slingshot slingshot;
    protected ArrayList<Bird> birds;
    protected ArrayList<Pig> pigs;
    protected ArrayList<Obstacle> obstacles;
    public int pauseButton_x = 720;
    public int pauseButton_y = 420;
    public int pauseButton_w = 75;
    public int pauseButton_h = 50;
    public int lossButton_x = 670;
    public int lossButton_y = 420;
    public int lossButton_w = 50;
    public int lossButton_h = 50;
    public int winButton_x = 620;
    public int winButton_y = 420;
    public int winButton_w = 50;
    public int winButton_h = 50;
    protected final float GRAVITY = -400f;
    protected final float MAX_DRAG_DISTANCE = 100f;
    protected final float LAUNCH_SPEED_MULTIPLIER = 600f;
    protected boolean explosionActive = false; // To track if an explosion is active
    protected float explosionDuration = 1f; // Duration for which the explosion is visible
    protected float explosionTimer = 0f; // Timer to track how long the explosion has been active
    Texture explosionTexture = new Texture("blast.png");
    protected boolean isDragging = false;
    protected final Texture bandTexture;
    protected final Vector2 anchorBack;  // Behind the bird
    protected final Vector2 anchorFront;// In front of the bird// Tracks if the bird is being dragged
    protected ShapeRenderer shapeRenderer;
    protected Texture backgroundTexture;
    protected Texture slingshot2;
    private Vector2 initialSlingshotPosition;
    private final Player player;
    protected Texture blastTexture;
    protected float blastTimer = 0f;
    protected Vector2 blastPosition = new Vector2();
    protected boolean isBlastActive = false;

    public Level(MainLauncher game, String t, int p1, int p2, int s1, int s2, Player player) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.slingshot = new Slingshot(t,p1,p2,s1,s2);
        this.birds = new ArrayList<>();
        this.pauseButton = new Texture("pausebutton.png");
        this.winButton = new Texture("win.png");
        this.lossButton = new Texture("loss.png");
        this.obstacles = new ArrayList<>();
        bandTexture = new Texture("band.png");
        anchorBack = new Vector2(slingshot.position.get(0) + 20, slingshot.position.get(1) + 130);
        anchorFront = new Vector2(slingshot.position.get(0) + 55, slingshot.position.get(1) + 130);
        shapeRenderer = new ShapeRenderer();
        this.initialSlingshotPosition = new Vector2(slingshot.position.get(0), slingshot.position.get(1));
        this.player = player;
        blastTexture = new Texture("blast.png");
    }

    public void dispose() {
        batch.dispose();
        slingshot.texture.dispose(); // Dispose the slingshot texture
        if (slingshot2 != null) {
            slingshot2.dispose(); // Dispose slingshot2 texture
        }
        if (backgroundTexture != null) {
            backgroundTexture.dispose(); // Dispose background texture
        }
        for (Bird bird : birds) {
            if (bird.texture != null) {
                bird.texture.dispose(); // Dispose each bird's texture
            }
        }
    }

//    protected boolean checkCollision(Bird bird, Pig pig) {
//        return bird.position.x < pig.position.x + pig.size.get(0) &&
//            bird.position.x + bird.size.get(0) > pig.position.x &&
//            bird.position.y < pig.position.y + pig.size.get(1) &&
//            bird.position.y + bird.size.get(1) > pig.position.y;
//    }
//
//    protected boolean checkCollision(Bird bird, Obstacle obstacle) {
//        return bird.position.x < obstacle.position.x + obstacle.width &&
//            bird.position.x + bird.size.get(0) > obstacle.position.x &&
//            bird.position.y < obstacle.position.y + obstacle.height &&
//            bird.position.y + bird.size.get(1) > obstacle.position.y;
//    }
//
//    protected void applyGravityDamage(float destroyedX, float destroyedY) {
//        for (Pig pig : pigs) {
//            if (isAbove(pig.position.x, pig.position.y, destroyedX, destroyedY)) {
//                pig.takeDamage(1);
//                pig.position.set(destroyedX, destroyedY);
//            }
//        }
//
//        for (Obstacle obstacle : obstacles) {
//            if (isAbove(obstacle.position.x, obstacle.position.y, destroyedX, destroyedY)) {
//                obstacle.takeDamage(1);
//                obstacle.position.set(destroyedX, destroyedY);
//            }
//        }
//    }
//
//    protected boolean isAbove(float x1, float y1, float x2, float y2) {
//        float xTolerance = 100f;
//        return Math.abs(x1 - x2) <= xTolerance && y1 > y2;
//    }
//
//    protected void handleCollision(Bird bird, Pig pig) {
//        if (checkCollision(bird, pig)) {
//            pig.takeDamage(bird.damage);
//            bird.isFlying = false;
//
//            if (bird instanceof Bombird) {
//                Bombird bombird = (Bombird) bird;
//                bombird.hasExploded = true;
//                explosionActive = true;
//                explosionTimer = 0f;
//
//                float birdCenterX = (bird.position.x + bird.size.get(0)) / 2;
//                float birdCenterY = (bird.position.y + bird.size.get(1)) / 2;
//                float explosionRadius = 80f; // Increased blast radius
//
//                for (Pig otherPig : pigs) {
//                    float pigCenterX = (otherPig.position.x + otherPig.size.get(0)) / 2;
//                    float pigCenterY = (otherPig.position.y + otherPig.size.get(1)) / 2;
//
//                    float distance = Vector2.dst(birdCenterX, birdCenterY, pigCenterX, pigCenterY);
//
//                    if (distance <= explosionRadius) {
//                        otherPig.takeDamage(3);
//                    }
//                    if (otherPig.isDestroyed){
//                        applyGravityDamage(otherPig.position.x, otherPig.position.y);
//                    }
//                }
//
//                for (Obstacle obstacle : obstacles) {
//                    float obstacleCenterX = (obstacle.position.x + obstacle.width) / 2;
//                    float obstacleCenterY = (obstacle.position.y + obstacle.height) / 2;
//
//                    float distance = Vector2.dst(birdCenterX, birdCenterY, obstacleCenterX, obstacleCenterY);
//                    if (distance <= explosionRadius) {
//                        obstacle.takeDamage(3);
//                    }
//                    if (obstacle.isDestroyed){
//                        applyGravityDamage(obstacle.position.x, obstacle.position.y);
//                    }
//
//                    if (obstacle instanceof TNT) {
//                        TNT tnt = (TNT) obstacle;
//                        tnt.hasExploded = true;
//                        handleTNTExplosion(tnt);
//                    }
//                }
//            }
//        }
//
//        if (pig.isDestroyed) {
//            applyGravityDamage(pig.position.x, pig.position.y);
//        }
//    }
//
//    protected void handleTNTExplosion(TNT tnt) {
//        float tntCenterX = tnt.position.x + tnt.width / 2;
//        float tntCenterY = tnt.position.y + tnt.height / 2;
//        float explosionRadius = 100f;
//
//        for (Pig pig : pigs) {
//            float pigCenterX = (pig.position.x + pig.size.get(0)) / 2;
//            float pigCenterY = (pig.position.y + pig.size.get(1)) / 2;
//
//            float distance = Vector2.dst(tntCenterX, tntCenterY, pigCenterX, pigCenterY);
//
//            if (distance <= explosionRadius) {
//                pig.takeDamage(3);
//            }
//            if (pig.isDestroyed){
//                applyGravityDamage(pig.position.x, pig.position.y);
//            }
//        }
//
//        for (Obstacle obstacle : obstacles) {
//            if (obstacle != tnt) {
//                float obstacleCenterX = (obstacle.position.x + obstacle.width) / 2;
//                float obstacleCenterY = (obstacle.position.y + obstacle.height) / 2;
//
//                float distance = Vector2.dst(tntCenterX, tntCenterY, obstacleCenterX, obstacleCenterY);
//
//                if (distance <= explosionRadius) {
//                    obstacle.takeDamage(3);
//                }
//                if (obstacle.isDestroyed){
//                    applyGravityDamage(obstacle.position.x, obstacle.position.y);
//                }
//
//                if (obstacle instanceof TNT && !((TNT) obstacle).hasExploded) {
//                    TNT chainTnt = (TNT) obstacle;
//                    chainTnt.hasExploded = true;
//                    handleTNTExplosion(chainTnt);
//                }
//            }
//        }
//    }
//
//    protected void handleCollision(Bird bird, Obstacle obstacle) {
//        if (checkCollision(bird, obstacle)) {
//            obstacle.takeDamage(bird.damage);
//            bird.isFlying = false;
//
//            if (obstacle instanceof TNT) {
//                TNT tnt = (TNT) obstacle;
//                tnt.hasExploded = true;
//                explosionActive = true;
//                explosionTimer = 0f;
//
//                float tntCenterX = (obstacle.position.x + obstacle.width) / 2;
//                float tntCenterY = (obstacle.position.y + obstacle.height) / 2;
//                float explosionRadius = 100f;
//
//                for (Pig pig : pigs) {
//                    float pigCenterX = (pig.position.x + pig.size.get(0)) / 2;
//                    float pigCenterY = (pig.position.x + pig.size.get(1)) / 2;
//
//                    float distance = Vector2.dst(tntCenterX, tntCenterY, pigCenterX, pigCenterY);
//
//                    if (distance <= explosionRadius) {
//                        pig.takeDamage(3);
//                    }
//                    if (pig.isDestroyed){
//                        applyGravityDamage(pig.position.x, pig.position.y);
//                    }
//                }
//
//                for (Obstacle otherObstacle : obstacles) {
//                    if (otherObstacle != obstacle) {
//                        float obstacleCenterX = (otherObstacle.position.x + otherObstacle.width) / 2;
//                        float obstacleCenterY = (otherObstacle.position.y + otherObstacle.height) / 2;
//
//                        float distance = Vector2.dst(tntCenterX, tntCenterY, obstacleCenterX, obstacleCenterY);
//                        if (distance <= explosionRadius) {
//                            otherObstacle.takeDamage(3);
//                        }
//                        if (otherObstacle.isDestroyed){
//                            applyGravityDamage(otherObstacle.position.x, otherObstacle.position.y);
//                        }
//                    }
//                }
//
//                if (obstacle.isDestroyed) {
//                    applyGravityDamage(obstacle.position.x, obstacle.position.y);
//                }
//            }
//        }
//    }
//
//    protected void clearScreen() {
//        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//    }
//
//    protected void drawGameElements() {
//        // Draw background
//        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        // Draw slingshot bands if dragging
//        if (isDragging) {
//            drawSlingshotBands();
//        }
//
//        // Draw game objects
//        drawSlingshot();
//        drawObstacles();
//        drawPigs();
//        drawUI();
//    }
//
//    protected void drawSlingshotBands() {
//        Bird firstBird = birds.get(0);
//        float birdX = firstBird.position.x;
//        float birdY = firstBird.position.y;
//
//        // Back band
//        float angleBack = MathUtils.atan2(birdY - anchorBack.y, birdX - anchorBack.x) * MathUtils.radiansToDegrees;
//        float lengthBack = Vector2.dst(anchorBack.x, anchorBack.y, birdX, birdY);
//        batch.draw(bandTexture, anchorBack.x, anchorBack.y, 0, 0, lengthBack, 10, 1, 1, angleBack,
//            0, 0, bandTexture.getWidth(), bandTexture.getHeight(), false, false);
//
//        // Front band
//        float angleFront = MathUtils.atan2(birdY - anchorFront.y, birdX - anchorFront.x) * MathUtils.radiansToDegrees;
//        float lengthFront = Vector2.dst(anchorFront.x, anchorFront.y, birdX, birdY);
//        batch.draw(bandTexture, anchorFront.x, anchorFront.y, 0, 0, lengthFront, 10, 1, 1, angleFront,
//            0, 0, bandTexture.getWidth(), bandTexture.getHeight(), false, false);
//    }
//
//    protected void drawSlingshot() {
//        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
//        drawBirds();
//        batch.draw(slingshot2, slingshot.position.get(0) - 5, slingshot.position.get(1),
//            slingshot.size.get(0), slingshot.size.get(1));
//    }
//
//    protected void drawBirds() {
//        for (Bird bird : birds) {
//            batch.draw(bird.texture, bird.position.x, bird.position.y,
//                bird.size.get(0), bird.size.get(1));
//        }
//    }
//
//    protected void drawObstacles() {
//        for (Obstacle obstacle : obstacles) {
//            obstacle.render(batch);
//        }
//    }
//
//    private void drawPigs() {
//        for (Pig pig : pigs) {
//            batch.draw(pig.texture, pig.position.x, pig.position.y,
//                pig.size.get(0), pig.size.get(1));
//        }
//    }
//
//    protected void drawUI() {
//        batch.draw(pauseButton, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h);
//        batch.draw(winButton, winButton_x, winButton_y, winButton_w, winButton_h);
//        batch.draw(lossButton, lossButton_x, lossButton_y, lossButton_w, lossButton_h);
//    }
//
//    protected void handleInput() {
//        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//            handleButtonClicks();
//        }
//    }
//
//    protected void handleButtonClicks() {
//        float mouseX = Gdx.input.getX();
//        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
//
//        if (isClickInBounds(mouseX, mouseY, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h)) {
//            game.setScreen(new PauseGame(game, 1, player));
//        }
//        if (isClickInBounds(mouseX, mouseY, winButton_x, winButton_y, winButton_w, winButton_h)) {
//            game.setScreen(new Win(game, player));
//        }
//        if (isClickInBounds(mouseX, mouseY, lossButton_x, lossButton_y, lossButton_w, lossButton_h)) {
//            game.setScreen(new Loss(game, 1, player));
//        }
//    }
//
//    protected boolean isClickInBounds(float x, float y, float buttonX, float buttonY, float width, float height) {
//        return x >= buttonX && x <= buttonX + width && y >= buttonY && y <= buttonY + height;
//    }
//
//
//    protected void updateFlyingBirds() {
//        Iterator<Bird> iterator = birds.iterator();
//        while (iterator.hasNext()) {
//            Bird bird = iterator.next();
//            if (bird.isFlying && bird.trajectoryPoints != null && !bird.trajectoryPoints.isEmpty()) {
//                Vector2 targetPoint = bird.trajectoryPoints.get(0);
//                updateBirdPosition(bird, targetPoint);
//
//                if (bird.position.y <= 60) {
//                    handleBirdLanding(bird, iterator);
//                    break;
//                }
//
//                if (bird.trajectoryPoints.isEmpty()) {
//                    bird.isFlying = false;
//                }
//            }
//        }
//    }
//
//    protected void updateBirdPosition(Bird bird, Vector2 targetPoint) {
//        float moveSpeed = 0.2f;
//        bird.position.x = MathUtils.lerp(bird.position.x, targetPoint.x, moveSpeed);
//        bird.position.y = MathUtils.lerp(bird.position.y, targetPoint.y, moveSpeed);
//
//        if (Vector2.dst(bird.position.x, bird.position.y, targetPoint.x, targetPoint.y) < 5f) {
//            bird.trajectoryPoints.remove(0);
//        }
//    }
//
//    protected void handleBirdLanding(Bird bird, Iterator<Bird> iterator) {
//        bird.isFlying = false;
//        iterator.remove();
//
//        if (!birds.isEmpty()) {
//            Bird nextBird = birds.get(0);
//            nextBird.position.set(90, 160);
//            nextBird.isFlying = false;
//            nextBird.velocity.setZero();
//        }
//    }
//
//    protected void handleCollisions() {
//        // Create safe copies of collections
//        List<Bird> activeBirds = new ArrayList<>(birds);
//        List<Pig> activePigs = new ArrayList<>(pigs);
//        List<Obstacle> activeObstacles = new ArrayList<>(obstacles);
//
//        // Tracking lists for removal
//        List<Bird> birdsToRemove = new ArrayList<>();
//        List<Pig> pigsToRemove = new ArrayList<>();
//        List<Obstacle> obstaclesToRemove = new ArrayList<>();
//
//        // Process flying birds using an iterator for safe removal
//        for (Bird bird : activeBirds) {
//            if (bird != null && bird.isFlying) {
//                // Check collisions with pigs
//                for (Pig pig : activePigs) {
//                    handleCollision(bird, pig);
//                    if (bird instanceof Bombird && ((Bombird) bird).hasExploded) {
//                        drawExplosion(bird);
//                    }
//                    if (pig.isDestroyed) {
//                        pigsToRemove.add(pig);
//                    }
//                }
//
//                // Check collisions with obstacles
//                for (Obstacle obstacle : activeObstacles) {
//                    handleCollision(bird, obstacle);
//                    if (obstacle instanceof TNT && ((TNT) obstacle).hasExploded) {
//                        drawTNTExplosion(obstacle);
//                    }
//                    if (obstacle.isDestroyed || (obstacle instanceof TNT && ((TNT) obstacle).hasExploded)) {
//                        obstaclesToRemove.add(obstacle);
//                    }
//                }
//
//                if (!bird.isFlying) {
//                    birdsToRemove.add(bird);
//                }
//            }
//        }
//
//        performCollisionCleanup(birdsToRemove, pigsToRemove, obstaclesToRemove);
//    }
//
//    protected void drawExplosion(Bird bird) {
//        batch.begin();
//        batch.draw(((Bombird) bird).blastTexture, bird.position.x, bird.position.y);
//        batch.end();
//    }
//
//    protected void drawTNTExplosion(Obstacle obstacle) {
//        batch.begin();
//        batch.draw(((TNT) obstacle).blastTexture, obstacle.position.x, obstacle.position.y);
//        batch.end();
//    }
//
//    protected void performCollisionCleanup(List<Bird> birdsToRemove, List<Pig> pigsToRemove, List<Obstacle> obstaclesToRemove) {
//        // Remove birds and reset next bird if needed
//        birds.removeAll(birdsToRemove);
//        if (!birds.isEmpty() && !birdsToRemove.isEmpty()) {
//            resetNextBird();
//        }
//
//        // Remove destroyed pigs and obstacles
//        pigs.removeAll(pigsToRemove);
//        obstacles.removeAll(obstaclesToRemove);
//    }
//
//    protected void resetNextBird() {
//        Bird nextBird = birds.get(0);
//        nextBird.position.set(90, 160);
//        nextBird.isFlying = false;
//        nextBird.velocity.x = 0;
//        nextBird.velocity.y = 0;
//    }
//
//    protected void checkGameConditions() {
//        if (pigs.isEmpty()) {
//            game.setScreen(new Win(game, player));
//            return;
//        }
//
//        if (birds.isEmpty()) {
//            // Make sure no birds are currently in flight
//            boolean birdInFlight = false;
//            for (Bird bird : birds) {
//                if (bird.isFlying) {
//                    birdInFlight = true;
//                    break;
//                }
//            }
//
//            // Only transition to lose screen if no birds are in flight
//            if (!birdInFlight) {
//                game.setScreen(new Loss(game, 1, player));
//                return;
//            }
//        }
//    }
//
//    protected void handleBirdRelease() {
//        if (!birds.isEmpty()) {
//            Bird bird = birds.get(0);
//            bird.launch();
//        }
//        isDragging = false;
//    }
//
//    protected void updateBirdPhysics(Bird bird, float delta) {
//        // Update position based on velocity
//        bird.position.x += bird.velocity.x * delta;
//        bird.position.y += bird.velocity.y * delta;
//
//        // Apply gravity to vertical velocity
//        bird.velocity.y += GRAVITY * delta;
//    }
//
//    protected void updateFlyingBirds(float delta) {
//        Iterator<Bird> iterator = birds.iterator();
//        while (iterator.hasNext()) {
//            Bird bird = iterator.next();
//            if (bird.isFlying) {
//                updateBirdPhysics(bird, delta);
//
//                // Check if bird has hit the ground
//                if (bird.position.y <= 60) {
//                    handleBirdLanding(bird, iterator);
//                    break;
//                }
//            }
//        }
//    }
//
//    protected ArrayList<Vector2> calculateTrajectoryPoints(float startX, float startY, float velocityX, float velocityY, float gravity) {
//        ArrayList<Vector2> points = new ArrayList<>();
//        float timestep = 1/60f; // Simulation timestep (60 FPS)
//        float maxTime = 5.0f;   // Maximum time to simulate
//
//        float posX = startX;
//        float posY = startY;
//        float velX = velocityX;
//        float velY = velocityY;
//
//        for (float t = 0; t < maxTime; t += timestep) {
//            // Add current position to trajectory
//            points.add(new Vector2(posX, posY));
//
//            // Update position and velocity using physics
//            posX += velX * timestep;
//            posY += velY * timestep;
//            velY += gravity * timestep;
//
//            // Stop if trajectory hits ground
//            if (posY <= 60) { // Adjust ground height as needed
//                break;
//            }
//        }
//
//        return points;
//    }
//
//    protected void updateBirdTrajectory() {
//        if (!isDragging || birds.isEmpty()) return;
//
//        Bird firstBird = birds.get(0);
//        int screenY = Gdx.graphics.getHeight() - Gdx.input.getY();
//
//        // Calculate launch velocity based on drag
//        float dragDX = initialSlingshotPosition.x - Gdx.input.getX();
//        float dragDY = initialSlingshotPosition.y - screenY;
//
//        float dragDistance = Math.min(Vector2.len(dragDX, dragDY), MAX_DRAG_DISTANCE);
//        float dragAngle = MathUtils.atan2(dragDY, dragDX);
//
//        // Calculate launch velocity
//        float launchSpeed = LAUNCH_SPEED_MULTIPLIER * (dragDistance / MAX_DRAG_DISTANCE);
//        float launchVelocityX = launchSpeed * MathUtils.cos(dragAngle);
//        float launchVelocityY = launchSpeed * MathUtils.sin(dragAngle);
//
//        // Store launch velocity in bird for later use
//        firstBird.launchVelocityX = launchVelocityX;
//        firstBird.launchVelocityY = launchVelocityY;
//
//        // Draw trajectory preview
//        drawTrajectoryPreview(firstBird, launchVelocityX, launchVelocityY);
//    }
//
//    protected void drawTrajectoryPreview(Bird bird, float velocityX, float velocityY) {
//        // Get trajectory points
//        ArrayList<Vector2> previewPoints = calculateTrajectoryPoints(
//            bird.position.x, bird.position.y, velocityX, velocityY, GRAVITY);
//
//        // Begin shape rendering
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        // Draw dotted line trajectory
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//
//        // Use white dots with fade out
//        int totalPoints = previewPoints.size();
//        float dotSpacing = 5; // Show a dot every X points
//        float dotSize = 3f;   // Size of trajectory dots
//
//        for (int i = 0; i < totalPoints; i += dotSpacing) {
//            Vector2 point = previewPoints.get(i);
//
//            // Calculate fade out based on position in trajectory
//            float alpha = 1.0f - ((float)i / totalPoints);
//            shapeRenderer.setColor(1, 1, 1, alpha * 0.7f);
//
//            // Draw circular dot
//            shapeRenderer.circle(point.x, point.y, dotSize * (1.0f - ((float)i / totalPoints)));
//        }
//
//        shapeRenderer.end();
//
//        Gdx.gl.glDisable(GL20.GL_BLEND);
//    }
}
