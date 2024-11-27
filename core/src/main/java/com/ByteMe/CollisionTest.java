package com.ByteMe;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CollisionTest {

    static class Bird {
        Vector2 position;
        ArrayList<Integer> size;

        public Bird(int x, int y, int width, int height) {
            this.position = new Vector2(x, y);
            this.size = new ArrayList<>();
            this.size.add(width);
            this.size.add(height);
        }
    }

    static class Pig {
        Vector2 position;
        ArrayList<Integer> size;

        public Pig(int x, int y, int width, int height) {
            this.position = new Vector2(x, y);
            this.size = new ArrayList<>();
            this.size.add(width);
            this.size.add(height);
        }
    }

    private boolean checkCollision(Bird bird, Pig pig) {
        return bird.position.x < pig.position.x + pig.size.get(0) &&
                bird.position.x + bird.size.get(0) > pig.position.x &&
                bird.position.y < pig.position.y + pig.size.get(1) &&
                bird.position.y + bird.size.get(1) > pig.position.y;
    }

    @Test
    void testCollision() {
        Bird bird = new Bird(0, 0, 10, 10);
        Pig pig = new Pig(5, 5, 10, 10);

        assertTrue(checkCollision(bird, pig), "Bird and Pig should collide");

        pig.position.x = 20;
        assertFalse(checkCollision(bird, pig), "Bird and Pig should not collide");

        pig.position.x = 9;
        assertTrue(checkCollision(bird, pig), "Bird and Pig should collide when touching");
    }
}
