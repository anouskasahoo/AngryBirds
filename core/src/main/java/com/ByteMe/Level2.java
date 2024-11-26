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

public class Level2 extends Level implements Screen , InputProcessor {

    private final Texture slingshot2;
    private final Texture backgroundTexture;
    private final Player player;
    private final MainLauncher game;
    private Vector2 initialPosition;
    private ShapeRenderer shapeRenderer;
    private Vector2 initialSlingshotPosition;
    private Set<Vector2> occupiedPositions;

    public Level2(MainLauncher game, Player player) {
        super(game, "slingshot1.png", 100, 70, 50, 150, player);
        slingshot2 = new Texture("slingshot2.png");
        backgroundTexture = new Texture("Level2_bg.png");
        this.player = player;
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        this.initialSlingshotPosition = new Vector2(slingshot.position.get(0), slingshot.position.get(1));


        // Initialize birds
        birds = new ArrayList<>();
        Bombird b1 = new Bombird();
        b1.position.set(90, 160);
        birds.add(b1);

        Bombird b2 = new Bombird();
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

        ClassicPig cp3 = new ClassicPig();
        cp3.position.set(628, 205);
        pigs.add(cp3);

        KingPig kp1 = new KingPig();
        kp1.position.set(720, 205);
        pigs.add(kp1);

        PrettyPig pp1 = new PrettyPig();
        pp1.position.set(585, 65);
        pigs.add(pp1);

        obstacles.add(new Wood(new Vector2(532, 70), Wood.Orientation.BOX));
        obstacles.add(new Wood(new Vector2(585, 110), Wood.Orientation.HORIZONTAL));
        obstacles.add(new Wood(new Vector2(580, 70), Wood.Orientation.VERTICAL));
        obstacles.add(new TNT(new Vector2(720, 70)));
        obstacles.add(new TNT(new Vector2(630, 70)));
        obstacles.add(new TNT(new Vector2(580, 115)));
        obstacles.add(new TNT(new Vector2(720, 160)));
        obstacles.add(new Wood(new Vector2(625, 155), Wood.Orientation.HORIZONTAL));
        obstacles.add(new Wood(new Vector2(670, 115), Wood.Orientation.VERTICAL));
        obstacles.add(new Wood(new Vector2(580, 160), Wood.Orientation.BOX));
        obstacles.add(new Wood(new Vector2(672, 205), Wood.Orientation.BOX));
        obstacles.add(new Stone(new Vector2(628, 160), Stone.Orientation.H_BOX));
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
        if (isBlastActive) {
            batch.draw(blastTexture, 300, 20, 700, 300);

            blastTimer += delta;
            if (blastTimer >= 0.5f) {
                isBlastActive = false;
                blastTimer = 0f;
            }
        }
        batch.end();
        initializeOccupiedPositions();
        handleInput();
        updateBirdTrajectory();
        updateFlyingBirds(delta);
        handleCollisions();
        checkGameConditions();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) throws IndexOutOfBoundsException{
        screenY = Gdx.graphics.getHeight() - screenY;
        try{
            Bird firstBird = birds.get(0);
            float birdX = firstBird.position.x;
            float birdY = firstBird.position.y;
            float birdWidth = firstBird.size.get(0);
            float birdHeight = firstBird.size.get(1);

            if (screenX >= birdX && screenX <= birdX + birdWidth && screenY >= birdY && screenY <= birdY + birdHeight) {
                isDragging = true;
                return true;
            }
            return false;
        }
        catch (IndexOutOfBoundsException e){
            checkGameConditions();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            screenY = Gdx.graphics.getHeight() - screenY;

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
        // Collect and process pigs first
        for (Pig pig : new ArrayList<>(pigs)) {
            if (isDirectlyAbove(pig.position.x, pig.position.y, destroyedX, destroyedY,
                pig.size.get(0), pig.size.get(1))) {
                pig.takeDamage(1);
                applyGravityFall(pig, destroyedY, pig.size.get(0), pig.size.get(1), occupiedPositions);
            }
        }

        // Then process obstacles
        for (Obstacle obstacle : new ArrayList<>(obstacles)) {
            if (isDirectlyAbove(obstacle.position.x, obstacle.position.y, destroyedX, destroyedY,
                (int)obstacle.width, (int)obstacle.height)) {
                obstacle.takeDamage(1);
                applyGravityFall(obstacle, destroyedY, (int)obstacle.width, (int)obstacle.height, occupiedPositions);
            }
        }
    }

    private void applyGravityFall(Object entity, float groundY, float width, float height, Set<Vector2> occupiedPositions) {
        Vector2 position = entity instanceof Pig ? ((Pig)entity).position : ((Obstacle)entity).position;

        float step = Math.min(height, 10f);
        Vector2 newPosition = new Vector2(position);

        // Precise fall with no overlap tolerance
        while (true) {
            Vector2 testPosition = new Vector2(newPosition.x, newPosition.y - step);

            // Check if falling is impossible
            if (testPosition.y - height <= groundY) {
                break;
            }

            // Boundary checks
            if (testPosition.x < 0 ||
                testPosition.x + width > 750 ||
                testPosition.y < 0 ||
                testPosition.y + height > 100) {
                break;
            }

            // Check for any overlap with ANY existing position
            boolean canFall = true;
            for (Vector2 occupiedPos : occupiedPositions) {
                if (wouldOverlap(testPosition, width, height, occupiedPos, width, height)) {
                    canFall = false;
                    break;
                }
            }

            // If overlap detected, stop falling
            if (!canFall) {
                break;
            }

            // If no overlap, update position
            newPosition.set(testPosition);
        }

        // Remove old position, update, and add new position
        occupiedPositions.remove(position);
        position.set(newPosition);
        occupiedPositions.add(newPosition);

        // Resolve any remaining overlaps
        resolveOverlaps(position, width, height, occupiedPositions);
    }

    private boolean wouldOverlap(Vector2 pos1, float width1, float height1,
                                 Vector2 pos2, float width2, float height2) {
        return !(pos1.x + width1 <= pos2.x ||   // Too far left
            pos1.x >= pos2.x + width2 ||   // Too far right
            pos1.y + height1 <= pos2.y ||  // Too far down
            pos1.y >= pos2.y + height2);   // Too far up
    }

    private boolean checkOverlap(Vector2 pos1, float width1, float height1,
                                 Vector2 pos2, float width2, float height2) {
        return !(pos1.x + width1 <= pos2.x ||
            pos1.x >= pos2.x + width2 ||
            pos1.y + height1 <= pos2.y ||
            pos1.y >= pos2.y + height2);
    }

    private void resolveOverlaps(Vector2 position, float width, float height, Set<Vector2> occupiedPositions) {
        // Additional pass to separate overlapping objects
        for (Vector2 occupiedPos : new ArrayList<>(occupiedPositions)) {
            if (checkOverlap(position, width, height, occupiedPos, width, height) &&
                !position.equals(occupiedPos)) {
                // Slightly adjust position to resolve overlap
                float adjustmentX = (position.x < occupiedPos.x) ? -width : width;

                // Boundary-aware adjustment
                if (position.x + adjustmentX < 0) {
                    adjustmentX = -position.x;
                }
                if (position.x + width + adjustmentX > 750) {
                    adjustmentX = 750 - (position.x + width);
                }
                if (position.y > 150) {
                    position.y = 150 - height;
                }
                position.x += adjustmentX;
                occupiedPositions.remove(occupiedPos);
                occupiedPositions.add(position);
            }
        }
    }

    private boolean isDirectlyAbove(float x1, float y1, float x2, float y2, float width, float height) {
        // More precise check for directly above objects
        return Math.abs(x1 - x2) <= width && y1 > y2;
    }

    private void handleCollision(Bird bird, Obstacle obstacle) {
        if (checkCollision(bird, obstacle)) {
            obstacle.takeDamage(bird.damage);
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
                    float pigCenterX = (otherPig.position.x + otherPig.size.get(0)) / 2;
                    float pigCenterY = (otherPig.position.y + otherPig.size.get(1)) / 2;

                    float distance = Vector2.dst(birdCenterX, birdCenterY, pigCenterX, pigCenterY);
                    if (distance <= explosionRadius) {
                        otherPig.takeDamage(3);
                    }
                    if (otherPig.isDestroyed) {
                        occupiedPositions.remove(otherPig.position); // Remove old position
                        //otherPig.position = findUniquePosition(otherPig.position, occupiedPositions, 70f, 70f);
                        applyGravityDamage(otherPig.position.x, otherPig.position.y);
                    }
                }

                for (Obstacle obstacle_ : obstacles) {
                    float obstacleCenterX = (obstacle_.position.x + obstacle_.width) / 2;
                    float obstacleCenterY = (obstacle_.position.y + obstacle_.height) / 2;

                    float distance = Vector2.dst(birdCenterX, birdCenterY, obstacleCenterX, obstacleCenterY);
                    if (distance <= explosionRadius) {
                        obstacle_.takeDamage(3);
                    }
                    if (obstacle_.isDestroyed) {
                        occupiedPositions.remove(obstacle_.position); // Remove old position
                        //obstacle.position = findUniquePosition(obstacle.position, occupiedPositions, 70f, 70f);
                        applyGravityDamage(obstacle_.position.x, obstacle_.position.y);
                    }

                    if (obstacle instanceof TNT) {
                        TNT tnt = (TNT) obstacle;
                        tnt.hasExploded = true;
                        handleTNTExplosion(tnt);
                    }
                }
            }

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

    private Vector2 findUniquePosition(Vector2 currentPos, Set<Vector2> occupiedPositions, Integer objectWidth, Integer objectHeight) {
        Vector2 newPos = new Vector2(currentPos);
        int attempts = 0;
        int maxAttempts = 200; // Increased attempts

        while (attempts < maxAttempts) {
            // Check if the new position conflicts with any existing positions
            boolean positionConflicts = occupiedPositions.stream()
                .anyMatch(existingPos -> isOverlapping(newPos, existingPos, objectWidth, objectHeight));

            if (!positionConflicts) {
                // If no conflict, add the position and return it
                occupiedPositions.add(newPos);
                return newPos;
            }

            // Try different strategies to find a unique position
            if (attempts < 50) {
                // Incrementally move position
                newPos.x += 20 + (attempts * 5);
                //newPos.y += 20 + (attempts * 5);
            } else {
                // More random repositioning in a wider area
                newPos.x = 600 + (int)(Math.random() * 200);
                //newPos.y = 70 + (int)(Math.random() * 200);
            }

            // Ensure position stays within reasonable game bounds
            newPos.x = Math.max(500, Math.min(newPos.x, 800));
            newPos.y = Math.max(50, Math.min(newPos.y, 250));

            attempts++;
        }

        // Fallback with guaranteed unique position
        newPos.set(
            600 + (int)(Math.random() * 200),
            70 + (int)(Math.random() * 200)
        );
        occupiedPositions.add(newPos);
        return newPos;
    }

    // Helper method to check for precise object overlap
    private boolean isOverlapping(Vector2 newPos, Vector2 existingPos, float width, float height) {
        return !(newPos.x + width < existingPos.x ||
            newPos.x > existingPos.x + width ||
            newPos.y + height < existingPos.y ||
            newPos.y > existingPos.y + height);
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
                        //otherPig.position = findUniquePosition(otherPig.position, occupiedPositions, 70f, 70f);
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
                        //obstacle.position = findUniquePosition(obstacle.position, occupiedPositions, 70f, 70f);
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
            //pig.position = findUniquePosition(pig.position, occupiedPositions, 70f, 70f);
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
                //pig.position = findUniquePosition(pig.position, occupiedPositions, 70f, 70f);
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
                    //obstacle.position = findUniquePosition(obstacle.position, occupiedPositions, 70f, 70f);
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
                        //drawExplosion(bird);
                        drawBlastEffect(bird.position.x, bird.position.y);
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
                        //drawTNTExplosion(obstacle);
                        drawBlastEffect(bird.position.x, bird.position.y);
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

    private void drawBlastEffect(float x, float y) {
        blastPosition.set(x, y);
        isBlastActive = true;
        blastTimer = 0f;
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


//package com.ByteMe;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.math.Vector2;
//
//import java.util.ArrayList;
//
//public class Level2 extends Level implements Screen {
//
//    private final Texture slingshot2;
//    private final Texture backgroundTexture;
//    private final Player player;
//    private final MainLauncher game;
//
//    public Level2(MainLauncher game, Player player) {
//        super(game,"slingshot1.png",100,70,50,150);
//        slingshot2 = new Texture("slingshot2.png");
//        backgroundTexture = new Texture("level2_bg.png");
//        this.game = game;
//        this.player = player;
//
//        // Initialize birds
//        birds = new ArrayList<>();
//        Bombird b1 = new Bombird();
//        b1.position.add(0);
//        b1.position.add(70);
//        birds.add(b1);
//
//        TeleBird b2 = new TeleBird();
//        b2.position.add(55);
//        b2.position.add(70);
//        birds.add(b2);
//
//        ClassicBird b3 = new ClassicBird();
//        b3.position.add(90);
//        b3.position.add(160);
//        birds.add(b3);
//
//
//        //Initialize pigs
//        pigs = new ArrayList<>();
//        ClassicPig cp1 = new ClassicPig();
//        cp1.position.add(677);
//        cp1.position.add(70);
//        pigs.add(cp1);
//
//        ClassicPig cp2 = new ClassicPig();
//        cp2.position.add(628);
//        cp2.position.add(115);
//        pigs.add(cp2);
//
//        ClassicPig cp3 = new ClassicPig();
//        cp3.position.add(628);
//        cp3.position.add(205);
//        pigs.add(cp3);
//
//        KingPig kp1 = new KingPig();
//        kp1.position.add(720);
//        kp1.position.add(205);
//        pigs.add(kp1);
//
//        PrettyPig pp1 = new PrettyPig();
//        pp1.position.add(585);
//        pp1.position.add(65);
//        pigs.add(pp1);
//
//        obstacles.add(new Wood(new Vector2(532, 70), Wood.Orientation.BOX));
//        obstacles.add(new Wood(new Vector2(585, 110), Wood.Orientation.HORIZONTAL));
//        obstacles.add(new Wood(new Vector2(580, 70), Wood.Orientation.VERTICAL));
//        obstacles.add(new TNT(new Vector2(720, 70)));
//        obstacles.add(new TNT(new Vector2(630, 70)));
//        obstacles.add(new TNT(new Vector2(580, 115)));
//        obstacles.add(new TNT(new Vector2(720, 160)));
//        obstacles.add(new Wood(new Vector2(625, 155), Wood.Orientation.HORIZONTAL));
//        obstacles.add(new Wood(new Vector2(670, 115), Wood.Orientation.VERTICAL));
//        obstacles.add(new Wood(new Vector2(580, 160), Wood.Orientation.BOX));
//        obstacles.add(new Wood(new Vector2(672, 205), Wood.Orientation.BOX));
//        obstacles.add(new Stone(new Vector2(628, 160), Stone.Orientation.H_BOX));
//        obstacles.add(new Stone(new Vector2(672, 115), Stone.Orientation.HORIZONTAL));
//        obstacles.add(new Stone(new Vector2(720, 115), Stone.Orientation.VERTICAL));
//        obstacles.add(new Stone(new Vector2(760, 115), Stone.Orientation.VERTICAL));
//    }
//
//    @Override
//    public void show() {
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//            float mouseX = Gdx.input.getX();
//            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
//
//            if (mouseX >= pauseButton_x && mouseX <= pauseButton_x+pauseButton_w && mouseY >= pauseButton_y && mouseY <= pauseButton_y+pauseButton_h){
//                game.setScreen(new PauseGame(game,2, player));
//            }
//            if (mouseX >= winButton_x && mouseX <= winButton_x+winButton_w && mouseY >= winButton_y && mouseY <= winButton_y+winButton_h){
//                game.setScreen(new Win(game, player));
//            }
//            if (mouseX >= lossButton_x && mouseX <= lossButton_x+lossButton_w && mouseY >= lossButton_y && mouseY <= lossButton_y+lossButton_h){
//                game.setScreen(new Loss(game,2, player));
//            }
//
//        }
//
//        batch.begin();
//        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        batch.draw(slingshot.texture, slingshot.position.get(0), slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
//        for (Bird bird : birds) {
//            batch.draw(bird.texture, bird.position.get(0), bird.position.get(1), bird.size.get(0), bird.size.get(1));
//        }
//
//        for (Obstacle obstacle : obstacles) {
//            obstacle.render(batch);
//        }
//
//        for (Pig pig : pigs) {
//            batch.draw(pig.texture, pig.position.get(0), pig.position.get(1), pig.size.get(0), pig.size.get(1));
//        }
//
//        batch.draw(pauseButton, pauseButton_x, pauseButton_y, pauseButton_w, pauseButton_h);
//        batch.draw(winButton, winButton_x, winButton_y, winButton_w, winButton_h);
//        batch.draw(lossButton, lossButton_x, lossButton_y, lossButton_w, lossButton_h);
//        batch.draw(slingshot2, slingshot.position.get(0) - 5, slingshot.position.get(1), slingshot.size.get(0), slingshot.size.get(1));
//        batch.end();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//    }
//
//    @Override
//    public void pause() {
//    }
//
//    @Override
//    public void resume() {
//    }
//
//    @Override
//    public void hide() {
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//        batch.dispose();
//        slingshot2.dispose();
//        backgroundTexture.dispose();
//        for (Bird bird : birds) {
//            if (bird.texture != null) {
//                bird.texture.dispose();
//            }
//        }
//    }
//}
