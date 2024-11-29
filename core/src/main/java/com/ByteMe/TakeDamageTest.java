package com.ByteMe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TakeDamageTest {

    static class SimplePig {
        float health;
        boolean isDestroyed = false;

        public void takeDamage(float damage) {
            health -= damage;
            if (health <= 0) {
                isDestroyed = true;
            }
        }
    }

    @Test
    void testTakeDamage() {
        SimplePig pig = new SimplePig();
        pig.health = 100f;

        assertEquals(100f, pig.health);

        pig.takeDamage(30f);
        assertEquals(70f, pig.health, 0.001);

        pig.takeDamage(70f);
        assertTrue(pig.isDestroyed);
        assertEquals(0f, pig.health, 0.001);
    }

    @Test
    void testTakeDamageWhenDestroyed() {
        SimplePig pig = new SimplePig();
        pig.health = 50f;

        pig.takeDamage(50f);
        assertTrue(pig.isDestroyed);

        pig.takeDamage(20f);
        assertTrue(pig.isDestroyed);
        assertEquals(-20f, pig.health, 0.001);
    }
}
