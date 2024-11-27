package com.ByteMe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Bird bird1;

    @BeforeEach
    void setUp() {
        // Initialize the game and birds
        game = new MainLauncher();

        // Create a bird object and add it to the birds list
        bird1 = new TeleBird();
        bird1.position = new Vector2(200, 300); // Arbitrary initial position
        bird1.isFlying = true;
        bird1.velocity = new Vector2(10, 15);

        game.birds = new ArrayList<>();
        game.birds.add(bird1);
    }

    @Test
    void testResetNextBird() {
        // Call the method to reset the next bird
        game.resetNextBird();

        // Retrieve the bird and check its properties
        Bird nextBird = game.birds.get(0);
        assertEquals(90, nextBird.position.x, "Bird's x-position should be reset to 90");
        assertEquals(160, nextBird.position.y, "Bird's y-position should be reset to 160");
        assertFalse(nextBird.isFlying, "Bird should not be flying after reset");
        assertEquals(0, nextBird.velocity.x, "Bird's x-velocity should be reset to 0");
        assertEquals(0, nextBird.velocity.y, "Bird's y-velocity should be reset to 0");
    }
}
